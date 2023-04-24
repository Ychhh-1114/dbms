package Keys;

import Configs.DBMSException;
import Elements.Column;
import Elements.Table;
import Elements.Tuple;

public class UniqueKey extends AbstractKey {

	public UniqueKey() {
		// TODO Auto-generated constructor stub
		this.constrName = "uniqueKey";
	}

	////////////////////////////////////////////////
	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLegal(Table table, Tuple tuple) {
		// TODO Auto-generated method stub
		for (String str : tuple.value.keySet())
			if (cols.contains(str)) {
				Column col = new Column(str);
				for (Column c : table.cols) {
					if (col.equals(c)) {
						col = c;
						break;
					}
				}
				if (col.value.contains(tuple.value.get(str)))
					return false;
				else
					continue;
			}
		return true;
	}

	@Override
	public boolean isLegal(Tuple tuple) {
		// TODO Auto-generated method stub
		return false;
	}

	/////////////////////////////////
	@Override
	public Key getKey(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isColumnIn(String cName) {
		// TODO Auto-generated method stub
		if (this.cols.contains(cName))
			return true;
		else
			return false;
	}

	@Override
	public boolean isLegal(Table table, String cName, String value) throws DBMSException {
		// TODO Auto-generated method stub
		for (Column col : table.cols) {
			if (col.name.equals(cName)) {
				if (col.value.contains(value))
					return false;
				else
					return true;
			}
		}
		throw new DBMSException("has not the column");
	}
}
