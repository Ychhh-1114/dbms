package AggregateFunction;

import Configs.DBMSException;
import Elements.Column;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;

public class Count implements Function {
	public String tName;
	public String cName;
	public String cValue;
	public AbstractType type;

	@Override
	public void setType(AbstractType type) {
		// TODO Auto-generated method stub

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
		this.cValue = String.valueOf(col.value.size());
	}

	@Override
	public void setName(String funcName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue(QueryTable table) {
		// TODO Auto-generated method stub
		return String.valueOf(table.queryTuples.size());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "count(" + this.cName + ")";
	}

	@Override
	public AbstractType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

}
