package Symbols;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AggregateFunction.FuncManage;
import AggregateFunction.Function;
import Configs.DBMSException;
import Elements.Column;
import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;

public class EqualsSign extends AbstractSymbol {

	public String tName = null;
	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");

	public EqualsSign() {
		// TODO Auto-generated constructor stub
		this.symbol = "=";
	}

	public boolean isConstrLegal(AbstractType type, String value) {
		AbstractType v1 = null;
		AbstractType v2 = null;
		try {
			v1 = type.getClass().newInstance();
			v1.setValue(this.cValue);
			v2 = type.getClass().newInstance();
			v2.setValue(value);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (v1.compareTo(v2) == 0)
			return true;
		else
			return false;
	}

	@Override
	public String getTname() {
		// TODO Auto-generated method stub
		return this.tName;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		Matcher matcher = detail.matcher(name);
		if (matcher.find()) {
			this.tName = matcher.group(1);
			this.cName = matcher.group(2);
		} else {
			this.cName = name;
		}
	}

	@Override
	public boolean isConstrLegal(QueryTable table) {
		// TODO Auto-generated method stub
		Function func = null;
		try {
			func = FuncManage.getFunc(this.cName, table);
		} catch (InstantiationException | IllegalAccessException | DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.cValue.equals(func.getValue(table));

	}

	@Override
	public boolean isConstrLegal(Table table, String name, String value) {
		// TODO Auto-generated method stub
		if (table instanceof QueryTable) {
			table = (QueryTable) table;
			for (String t : ((QueryTable) table).tcp.keySet()) {
				if (((QueryTable) table).tcp.get(t).containsKey(name)) {

					AbstractType type = ((QueryTable) table).tcp.get(t).get(this.cName);
					AbstractType v1 = null;
					AbstractType v2 = null;
					try {
						v1 = type.getClass().newInstance();
						v1.setValue(this.cValue);
						v2 = type.getClass().newInstance();
						v2.setValue(value);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (v1.compareTo(v2) == 0)
						return true;
					else
						return false;

				}
			}
			return false;

		} else {
			Column col = new Column(name);
			for (Column c : table.cols)
				if (c.equals(col)) {
					col = c;
					break;
				}

			AbstractType v1 = null;
			AbstractType v2 = null;
			try {

				v1 = col.type.getClass().newInstance();
				v1.setValue(cValue);
				v2 = col.type.getClass().newInstance();
				v2.setValue(value);

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (v1.compareTo(v2) == 0)
				return true;
			else
				return false;

		}

	}

}
