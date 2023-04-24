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

public class Sum implements Function {
	public String cName;
	public String cValue;
	public String tName;
	public AbstractType type;

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
		if (col.type instanceof DBVarchar) {
			DataBase.isRightRe = false;
			throw new DBMSException("varchar doesn't support summarize function!");
		}

		double sum = 0;
		for (String v : col.value) {
			sum += Double.parseDouble(v);
		}

		this.cValue = String.valueOf(sum);

	}

	@Override
	public void setName(String funcName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue(QueryTable table) {
		try {
			// TODO Auto-generated method stub
			if (this.type instanceof DBVarchar) {
				DataBase.isRightRe = false;
				throw new DBMSException("this type can't summarize!");
			}

		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double sum = 0;
		for (ConnTuple tuple : table.queryTuples) {
			if (this.tName != null) {
				sum += (Double) (type.getValue(tuple.tcp.get(this.tName).get(this.cName)));
			} else {
				boolean find = false;
				for (String t : tuple.tcp.keySet()) {
					if (tuple.tcp.get(t).containsKey(this.cName)) {
						sum += (Double) type.getValue(tuple.tcp.get(t).get(cName));
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
		return String.valueOf(sum);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "sum(" + this.cName + ")";
	}

	@Override
	public AbstractType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
}
