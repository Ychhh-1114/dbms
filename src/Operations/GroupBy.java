package Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.ConnTuple;
import Elements.QueryTable;

public class GroupBy extends AbstractOperation implements Segment, Comparable {

	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");

	public String data;
	public String beforeData;
	public String processData;

	public Having having;
	public int reSort = 2;

	public HashMap<String, QueryTable> groups = new HashMap<String, QueryTable>();

	public HashMap<String, ArrayList<String>> tcg;
	public HashSet<String> byCols = new HashSet();

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
		return 2;
	}

	@Override
	public String afterProcess() {
		// TODO Auto-generated method stub
		return "group.*";
	}

	@Override
	public void getProcess(String data) {
		// TODO Auto-generated method stub
		this.beforeData = data;
		if (this.beforeData == null)
			this.beforeData = "";
		Pattern pattern = Pattern.compile("(.*)\s*" + this.beforeData);
		Matcher matcher = pattern.matcher(this.data);
		if (matcher.find()) {
			this.processData = matcher.group(1);
		}
		Having h = new Having();
		if (h.isHasHving(this.processData))
			having = h;

	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		String cols[] = this.processData.split(",");
		for (String c : cols)
			byCols.add(c.trim());
		if (having != null)
			having.process();
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

	public boolean isLegal(QueryTable table) throws DBMSException {

		for (String col : byCols) {
			Matcher matcher = detail.matcher(col);
			if (matcher.find()) {
				String tName = matcher.group(1).trim();
				String cName = matcher.group(2).trim();
				boolean find = false;
				for (String name : table.tcp.keySet()) {
					if (name.equals(tName)) {
						HashSet<String> cols = (HashSet<String>) table.tcp.get(tName).keySet();
						if (cols.contains(cName)) {
							find = true;
							if (this.tcg.containsKey(tName))
								this.tcg.get(tName).add(cName);
							else {
								this.tcg.put(tName, new ArrayList<String>());
								this.tcg.get(tName).add(cName);
							}
							break;
						} else
							throw new DBMSException("the table has not the column!");
					}
				}

				if (!find)
					throw new DBMSException("the table has not load in!");
			} else {
				boolean find = false;
				for (String t : table.tcp.keySet()) {
					Set<String> cols = table.tcp.get(t).keySet();
					if (cols.contains(col)) {
						find = true;
						if (this.tcg.containsKey(t))
							this.tcg.get(t).add(col);
						else {
							this.tcg.put(t, new ArrayList<String>());
							this.tcg.get(t).add(col);
						}
					}
				}
				if (!find)
					throw new DBMSException("has not the column!");
			}
		}
		return true;
	}

	@Override
	public void constrTable(QueryTable table) {
		// TODO Auto-generated method stub
		this.tcg = new HashMap<String, ArrayList<String>>();
		boolean again = false;
		try {
			again = isLegal(table);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (again) {
			table.groups = new HashMap<String, QueryTable>();
			table.isGrouped = true;
			for (ConnTuple tuple : table.queryTuples) {
				String val = tuple.getValue(this.tcg);
				if (table.groups.containsKey(val)) {
					if (!table.groups.containsKey(val)) {
						table.groups.put(val, new QueryTable());
						table.groups.get(val).queryTuples.add(tuple);
					} else
						table.groups.get(val).queryTuples.add(tuple);

				} else {
					table.groups.put(val, new QueryTable());
					table.groups.get(val).tcp = table.tcp;
					table.groups.get(val).queryTuples.add(tuple);
				}
			}

			if (this.having != null)
				having.constrTable(table);

			table.transGroups();

		}
	}

}
