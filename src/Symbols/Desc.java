package Symbols;

import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AggregateFunction.FuncManage;
import AggregateFunction.Function;
import Configs.DBMSException;
import Elements.ConnTuple;
import Elements.DataBase;
import Elements.QueryTable;
import Type.AbstractType;

public class Desc implements SortSymbol {
	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");
	public String tName;
	public String cName;

	@Override
	public void sort(QueryTable table) {
		// TODO Auto-generated method stub

		class MySort implements Comparator<ConnTuple> {
			@Override
			public int compare(ConnTuple o1, ConnTuple o2) {
				// TODO Auto-generated method stub

				AbstractType type = null;
				if (tName != null)
					type = table.tcp.get(tName).get(cName);
				else {
					for (String t : table.tcp.keySet())
						if (table.tcp.get(t).containsKey(cName)) {
							type = table.tcp.get(t).get(cName);
							tName = t;
						}

					if (type == null) {
						DataBase.isRightRe = false;
						try {
							throw new DBMSException("table has not the column");
						} catch (DBMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
				AbstractType v1 = null;
				AbstractType v2 = null;
				try {
					v1 = type.getClass().newInstance();
					v1.setValue(o1.tcp.get(tName).get(cName));
					v2 = type.getClass().newInstance();
					v2.setValue(o2.tcp.get(tName).get(cName));

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (v1.compareTo(v2) == 0)
					return 0;
				else if (v1.compareTo(v2) > 0)
					return -1;
				else
					return 1;
			}

		}

		Collections.sort(table.queryTuples, new MySort());

	}

	@Override
	public void sortTables(QueryTable table) {
		// TODO Auto-generated method stub
		class MySort implements Comparator<QueryTable> {
			@Override
			public int compare(QueryTable o1, QueryTable o2) {
				Function func1 = null;
				Function func2 = null;
				AbstractType type = null;
				try {
					// TODO Auto-generated method stub
					func1 = FuncManage.getFunc(cName, o1);
					func2 = FuncManage.getFunc(cName, o2);
				} catch (InstantiationException | IllegalAccessException | DBMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				GreaterThan greaterThan = new GreaterThan();
				greaterThan.setName(cName);
				greaterThan.cValue = func1.getValue(o2);
				type = func1.getType();
				if (greaterThan.isConstrLegal(type, func1.getValue(o1)))
					return -1;
				else
					return 1;

			}

		}

		Collections.sort(table.groupTables, new MySort());

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		Matcher matcher = detail.matcher(name);
		if (matcher.find()) {
			this.tName = matcher.group(1).trim();
			this.cName = matcher.group(2).trim();
		} else
			this.cName = name.trim();

	}

}
