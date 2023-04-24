package Operations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.QueryTable;
import Elements.Table;
import Users.PrivilegeManage;

public class QueryControl {

	static private Pattern fRegex = Pattern.compile(".*from\s+(.*)");
	static private Pattern wRegex = Pattern.compile(".*where\s+(.*)");
	static private Pattern jRegex = Pattern.compile(".*join\s+(.*)");
	static private Pattern gRegex = Pattern.compile(".*group\s+by\s+(.*)");
	static private Pattern oRegex = Pattern.compile(".*order\s+by\s+(.*)");

	static public HashMap<Pattern, Class> segmentCreater = new HashMap();
	static {
		segmentCreater.put(wRegex, Where.class);
		segmentCreater.put(jRegex, JoinOn.class);
		segmentCreater.put(gRegex, GroupBy.class);
		segmentCreater.put(oRegex, OrderBy.class);
		segmentCreater.put(fRegex, From.class);

	}

	public TreeSet<Segment> query = new TreeSet();

	public void getQueryControl(String sql) throws InstantiationException, IllegalAccessException {
		for (Pattern pattern : segmentCreater.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {

				Segment segment = (Segment) segmentCreater.get(pattern).newInstance();
				segment.setData(matcher.group(1));
				query.add(segment);
			}
		}

	}

	public QueryTable launch(Select select)
			throws DBMSException, CloneNotSupportedException, InstantiationException, IllegalAccessException {
		String next = null;
		ArrayList<String> tList = new ArrayList();
		for (Segment s : query) {
			s.getProcess(next);
			s.process();
			next = s.afterProcess();
			if (s.getTables() != null)
				tList.addAll(s.getTables());
		}

		select.processSelect(tList);

		if (!PrivilegeManage.isPrivilage(select))
			throw new DBMSException("the user has not the privilege!");
		/*
		 * 有连接先连接 连接完再筛选 【筛选后再分组 分完组再having】 最后排序 输出
		 *
		 */

		class reSort implements Comparator<Segment> {
			@Override
			public int compare(Segment o1, Segment o2) {
				// TODO Auto-generated method stub
				if (o1.getResort() == o2.getResort())
					return 0;
				else if (o1.getResort() > o2.getResort())
					return 1;
				else
					return -1;
			}
		}

		TreeSet<Segment> newSort = new TreeSet(new reSort());
		for (Segment s : query)
			if (!(s instanceof From))
				newSort.add(s);

		QueryTable table = null;
		if (select.selectmap.keySet().size() > 1) {
			// 有join on
			JoinOn head = (JoinOn) newSort.first();
			newSort.remove(newSort.first());

			head.getConnect(select.selectmap.keySet());
			table = head.conntable;
		} else {
			for (Table t : select.selectmap.keySet()) {
				table = QueryTable.updateToQuery(t);
				break;
			}
		}

		for (Segment segment : newSort)
			segment.constrTable(table);

		return table;

	}

}
