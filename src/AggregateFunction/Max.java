package AggregateFunction;

import java.util.HashMap;
import java.util.TreeSet;

import Configs.DBMSException;
import Elements.Column;
import Elements.ConnTuple;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;
import Type.DBDouble;
import Type.DBInt;
import Type.DBVarchar;

public class Max implements Function {
	public String tName = null;
	public String cName = null;
	public String cValue = null;
	public AbstractType type = null;

	@Override
	public void setType(AbstractType type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	@Override
	public void setName(String tName, String cName) {
		// TODO Auto-generated method stub
		this.cName = cName;
		this.tName = tName;
	}

	@Override
	public void getValue(Table table) throws DBMSException {
		// TODO Auto-generated method stub
		Column col = new Column(this.cName);
		boolean find = false;
		for (Column c : table.cols)
			if (c.equals(col)) {
				col = c;
				find = true;
				break;
			}

		if (!find) {
			DataBase.isRightRe = false;
			throw new DBMSException("has not this column!");
		}

		TreeSet<AbstractType> valueSet = new TreeSet();
		for (String v : col.value) {
			try {
				AbstractType val = col.type.getClass().newInstance();
				val.setValue(v);
				valueSet.add(val);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.cValue = valueSet.last().getValue();

	}

	@Override
	public void setName(String funcName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue(QueryTable table) {
		// TODO Auto-generated method stub
		TreeSet set = null;
		if (this.type instanceof DBDouble)
			set = new TreeSet<Double>();
		else if (type instanceof DBInt)
			set = new TreeSet<Integer>();
		else
			set = new TreeSet<String>();
		for (ConnTuple tuple : table.queryTuples) {
			if (this.tName != null) {
				set.add(type.getValue(tuple.tcp.get(this.tName).get(this.cName)));
			} else {
				boolean find = false;
				for (String t : tuple.tcp.keySet()) {
					if (tuple.tcp.get(t).containsKey(this.cName)) {
						set.add(tuple.tcp.get(t).get(cName));
						find = true;
						break;
					}
				}
				if (!find) {
					DataBase.isRightRe = false;
					try {
						throw new DBMSException("has not the column");
					} catch (DBMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		return String.valueOf(set.last());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "max(" + this.cName + ")";
	}

	@Override
	public AbstractType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
}
