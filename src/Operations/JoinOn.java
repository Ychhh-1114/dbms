package Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.Column;
import Elements.ConnTuple;
import Elements.QueryTable;
import Elements.Table;
import Elements.Tuple;
import Type.AbstractType;

public class JoinOn extends AbstractOperation implements Segment, Comparable {

	public int reSort = 0;
	public String data;
	public String beforeData;

	public QueryTable conntable;
	public String connTname;
	public String connParams;

	public ConnWhere connWhere;

	public String tName1 = null;
	public String cName1 = null;

	public String tName2 = null;
	public String cName2 = null;

//	public HashMap<String,String> table1 = new HashMap<String, String>();
//	public HashMap<String,String> table2 = new HashMap<String, String>();

//	public HashMap<HashMap<String,String>,HashMap<String,String>> conn = new HashMap();

	static private Pattern compRegex = Pattern.compile("(.*)\s+=\s+(.*)");
	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");

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
		return 3;
	}

	@Override
	public void getProcess(String data) {
		// TODO Auto-generated method stub
		this.beforeData = data;
		if (this.beforeData == null)
			this.beforeData = "";
		String regex = "(.*)\s+on(.*)\s*" + data;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(this.data);
		if (matcher.find()) {
			this.connTname = matcher.group(1);
			this.connParams = matcher.group(2);
		}

	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

		Matcher matcher = compRegex.matcher(this.connParams);
		if (matcher.find()) {
			String attr1 = matcher.group(1).trim();
			String attr2 = matcher.group(2).trim();
			matcher = detail.matcher(attr1);
			if (matcher.find()) {
				tName1 = matcher.group(1).trim();
				cName1 = matcher.group(2).trim();

			}

			matcher = detail.matcher(attr2);
			if (matcher.find()) {
				tName2 = matcher.group(1).trim();
				cName2 = matcher.group(2).trim();

			}

		}

//		connWhere = new ConnWhere(connParams);

	}

	@Override
	public String afterProcess() {
		// TODO Auto-generated method stub
		return "join.*";
	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		this.data = data;
	}

	@Override
	public ArrayList<String> getTables() {
		// TODO Auto-generated method stub
		ArrayList<String> tNames = new ArrayList<String>();
		tNames.add(connTname);
		return tNames;
	}

	@Override
	public int getResort() {
		// TODO Auto-generated method stub
		return this.reSort;
	}

	public void getConnect(Set<Table> tables) throws CloneNotSupportedException {
		ArrayList<Table> tList = new ArrayList<Table>();
		for (Table t : tables)
			tList.add(t);

		Table table1 = tList.get(tList.indexOf(new Table(tName1)));
		Table table2 = tList.get(tList.indexOf(new Table(tName2)));

		HashMap<String, HashMap<String, AbstractType>> tcp = new HashMap();
		for (Column col : table1.cols) {
			HashMap<String, AbstractType> cp = new HashMap();
			cp.put(col.name, col.type);
			if (tcp.containsKey(tName1))
				tcp.get(tName1).putAll(cp);
			else {
				tcp.put(tName1, new HashMap<String, AbstractType>());
				tcp.get(tName1).putAll(cp);
			}
		}

		for (Column col : table2.cols) {
			HashMap<String, AbstractType> cp = new HashMap();
			cp.put(col.name, col.type);
			if (tcp.containsKey(tName1))
				tcp.get(tName2).putAll(cp);
			else {
				tcp.put(tName2, new HashMap<String, AbstractType>());
				tcp.get(tName2).putAll(cp);
			}
		}

		ArrayList<ConnTuple> connTuples = new ArrayList<ConnTuple>();
		for (Tuple t1 : table1.tuples) {
			for (Tuple t2 : table2.tuples) {
				if (t1.value.get(cName1).equals(t2.value.get(cName2))) {
					ConnTuple temp = new ConnTuple();
					temp.addValue(tName1, t1.value);
					temp.addValue(tName2, t2.value);

					connTuples.add(temp);
				}

			}
		}

		this.conntable = QueryTable.constrTable(connTuples, tcp);

	}

	@Override
	public void constrTable(QueryTable table) {
		// TODO Auto-generated method stub

	}

}
