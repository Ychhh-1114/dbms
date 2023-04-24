package Keys;

import Configs.DBMSException;
import Elements.Table;
import Elements.Tuple;

public class DefaultKey extends AbstractKey {

	@Override
	public String getKeyName() {
		// TODO Auto-generated method stub
		return this.constrName;
	}

	@Override
	public Key getKey(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		return null;
	}

	//////////////////////////
	public DefaultKey() {
		// TODO Auto-generated constructor stub
		this.constrName = "defaultKey";
	}

	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLegal(Table table, Tuple tuple) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLegal(Tuple tuple) {
		// TODO Auto-generated method stub
		return true;
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
