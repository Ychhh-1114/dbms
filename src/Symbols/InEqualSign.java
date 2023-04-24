package Symbols;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AggregateFunction.FuncManage;
import AggregateFunction.Function;
import Configs.DBMSException;
import Elements.Column;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;
import Type.DBDouble;
import Type.DBInt;
import Type.DBVarchar;

public class InEqualSign extends AbstractSymbol {
	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");
	public String tName;

	public InEqualSign() {
		// TODO Auto-generated constructor stub
		this.symbol = "<=>";
	}

	@Override
	public boolean isConstrLegal(Table table, String name, String value) {
		// TODO Auto-generated method stub
		if (table instanceof QueryTable) {

			table = (QueryTable) table;
			AbstractType type = ((QueryTable) table).tcp.get(this.tName).get(this.cName);
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

			if (v1.compareTo(v2) != 0)
				return true;
			else
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

			if (v1.compareTo(v2) >= 0)
				return true;
			else
				return false;
		}

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		Matcher matcher = detail.matcher(name);
		if (matcher.find()) {
			this.tName = matcher.group(1);
			this.cName = matcher.group(2);
		} else
			this.cName = name;
	}

	@Override
	public boolean isConstrLegal(QueryTable table) {
		// TODO Auto-generated method stub
		Function func = null;
		AbstractType type = null;
		for (String tName : table.tcp.keySet()) {
			if (table.tcp.get(tName).containsKey(this.cName)) {
				type = table.tcp.get(tName).get(this.cName);
				break;
			}
		}
		if (type == null) {
			DataBase.isRightRe = false;
			try {
				throw new DBMSException("the column has not the column");
			} catch (DBMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		AbstractType v1 = null, v2 = null;
		if (type instanceof DBVarchar) {
			v1 = new DBVarchar();
			v2 = new DBVarchar();
		} else if (type instanceof DBDouble) {
			v1 = new DBDouble();
			v2 = new DBDouble();
		} else {
			v1 = new DBInt();
			v2 = new DBInt();
		}

		try {
			func = FuncManage.getFunc(this.cName, table);
			v1.setValue(func.getValue(table));
			v2.setValue(this.cValue);
		} catch (InstantiationException | IllegalAccessException | DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (v1.compareTo(v2) != 0)
			return true;
		else
			return false;
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

		if (v1.compareTo(v2) != 0)
			return true;
		else
			return false;
	}

	@Override
	public String getTname() {
		// TODO Auto-generated method stub
		return this.tName;
	}
}
