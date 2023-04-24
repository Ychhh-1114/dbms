package Keys;

import Configs.DBMSException;
import Elements.Table;
import Elements.Tuple;

public class NotnullKey extends AbstractKey {

	public NotnullKey() {
		// TODO Auto-generated constructor stub
		this.constrName = "NotnullKey";
	}

	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Key getKey(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		return null;
	}

	//////////////////////////////////////////
	@Override
	public boolean isLegal(Table table, Tuple tuple) {
		// TODO Auto-generated method stub
		for (String str : cols) {
			if (!tuple.value.containsKey(str))
				return false;
		}
		return true;
	}

	@Override
	public boolean isLegal(Tuple tuple) {
		// TODO Auto-generated method stub
		return false;
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
		return false;
	}

}
