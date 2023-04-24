package Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.QueryTable;
import Elements.Table;
import Symbols.Asc;
import Symbols.Desc;
import Symbols.SortSymbol;

public class OrderBy extends AbstractOperation implements Segment, Comparable {

	public String data;
	public String by;
	public String byName;
	public int reSort = 3;

	public ArrayList<SortSymbol> sortList = new ArrayList();

	static private Pattern desc = Pattern.compile("(.*)\s+desc\s*");
	static private Pattern asc = Pattern.compile("(.*)\s+asc\s*");
	static private HashMap<Pattern, Class> byRegex = new HashMap();
	static {
		byRegex.put(desc, Desc.class);
		byRegex.put(asc, Asc.class);
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Segment segment = (Segment) o;
		if (this.getPriority() < segment.getPriority())
			return -1;
		else if (this.getPriority() == segment.getPriority())
			return 0;
		else
			return 1;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		String sorts[] = this.data.split(",");
		for (String str : sorts) {
			for (Pattern pattern : byRegex.keySet()) {
				Matcher matcher = pattern.matcher(str);

				if (matcher.find()) {
					try {
						SortSymbol sort = (SortSymbol) byRegex.get(pattern).newInstance();
						sort.setName(matcher.group(1));
						this.sortList.add(sort);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}

	@Override
	public void getProcess(String data) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public String afterProcess() {
		// TODO Auto-generated method stub
		return "order\s+by.*" + data;
	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		this.data = data;
	}

	@Override
	public ArrayList<String> getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResort() {
		// TODO Auto-generated method stub
		return this.reSort;
	}

	@Override
	public void constrTable(QueryTable table) {
		// TODO Auto-generated method stub
		if (table.groups.size() == 0) {
			for (SortSymbol s : sortList)
				s.sort(table);

		} else {

		}
	}

}
