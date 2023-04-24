package Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import AggregateFunction.Function;
import Operations.Select;
import Type.AbstractType;

// 查询表不需要进行序列化，只在内存中进行操作
public class QueryTable extends Table {

	public HashMap<String, HashMap<String, AbstractType>> tcp = new HashMap<String, HashMap<String, AbstractType>>();
	public ArrayList<ConnTuple> queryTuples = new ArrayList<ConnTuple>();
//	public TreeSet<ConnTuple> queryTuples = new TreeSet();
	public HashMap<String, QueryTable> groups = new HashMap<String, QueryTable>();
	public ArrayList<QueryTable> groupTables = new ArrayList<QueryTable>();

	public boolean isGrouped = false;

	public QueryTable() {
		// TODO Auto-generated constructor stub
	}

	public QueryTable(Table table) {
		// TODO Auto-generated constructor stub

	}

	static public QueryTable constrTable(ArrayList<ConnTuple> list,
			HashMap<String, HashMap<String, AbstractType>> map) {
		QueryTable table = new QueryTable();
		table.queryTuples.addAll(list);
		table.tcp.putAll(map);
		return table;

	}

	static public QueryTable updateToQuery(Table t) throws CloneNotSupportedException {
		QueryTable table = new QueryTable();
		for (Column c : t.cols) {
			if (table.tcp.containsKey(t.tName)) {
				HashMap<String, AbstractType> cp = new HashMap<String, AbstractType>();
				cp.put(c.name, c.type);
				if (!table.tcp.get(t.tName).containsKey(c.name))
					table.tcp.get(t.tName).putAll(cp);
			} else {
				table.tcp.put(t.tName, new HashMap<String, AbstractType>());
				HashMap<String, AbstractType> cp = new HashMap<String, AbstractType>();
				cp.put(c.name, c.type);
				table.tcp.get(t.tName).putAll(cp);
			}
		}

		for (Tuple tuple : t.tuples) {
			ConnTuple connTuple = ConnTuple.updateToConn(tuple, t.tName);
			table.queryTuples.add(connTuple);
		}

		return table;
	}
//	

	public void showGroups(Select select) {
		if (select.isAll) {
			for (String tName : this.tcp.keySet()) {
				for (String cName : this.tcp.get(tName).keySet()) {
					System.out.print(cName + "    ");
				}
			}
			System.out.println();
			for (QueryTable table : this.groupTables) {
				ConnTuple tuple = table.queryTuples.get(0);
				tuple.show(select, true);
			}
		} else if (select.isFunc) {
			for (Function func : select.funcs) {
				System.out.print(func.getName() + "    ");
			}
			System.out.println();
			for (QueryTable table : this.groupTables) {
				for (Function func : select.funcs) {
					System.out.print(func.getValue(table) + "    ");
				}
			}
			System.out.println();
		} else {
			for (Table table : select.selectmap.keySet()) {
				for (String cName : select.selectmap.get(table)) {
					System.out.print(cName + "    ");
				}
			}
			System.out.println();
			for (QueryTable table : this.groupTables) {
				ConnTuple tuple = table.queryTuples.get(0);
				tuple.show(select, false);
			}
		}
	}

	public void showQuery(Select select) {
		if (select.isAll) {
			for (String tName : this.tcp.keySet()) {
				for (String cName : this.tcp.get(tName).keySet()) {
					System.out.print(cName + "    ");
				}
			}
			System.out.println();
			for (ConnTuple tuple : this.queryTuples) {
				for (String tName : this.tcp.keySet()) {
					for (String cName : this.tcp.get(tName).keySet()) {
						System.out.print(tuple.tcp.get(tName).get(cName) + "    ");
					}
				}
				System.out.println();
			}

		} else if (select.isFunc) {
			for (Function func : select.funcs) {
				System.out.print(func.getName() + "    ");
			}
			System.out.println();
			for (Function func : select.funcs) {
				System.out.print(func.getValue(this) + "    ");
			}
			System.out.println();
		} else {
			for (Table table : select.selectmap.keySet()) {
				for (String cName : select.selectmap.get(table)) {
					System.out.print(cName + "    ");
				}
			}
			System.out.println();
			for (ConnTuple tuple : this.queryTuples) {
				for (Table table : select.selectmap.keySet()) {
					for (String cName : select.selectmap.get(table)) {
						System.out.print(tuple.tcp.get(table.tName).get(cName) + "    ");
					}
				}
				System.out.println();
			}
		}
	}

	public void show(Select select) {
		if (DataBase.isRightRe) {
			if (this.groups.size() != 0)
				this.showGroups(select);
			else
				this.showQuery(select);
		} else
			DataBase.isRightRe = true;

	}

	public void transGroups() {
		groupTables.clear();
		for (QueryTable table : this.groups.values())
			groupTables.add(table);

	}
}
