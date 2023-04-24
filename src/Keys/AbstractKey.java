package Keys;

import java.io.Serializable;
import java.util.HashSet;

import Configs.DBMSException;
import Elements.Column;
import Elements.Table;

public abstract class AbstractKey implements Key, Serializable {

	public HashSet<String> cols = new HashSet<String>(); // store the name of each column,if need Column type must load
															// it

	@Override
	public String getKeyName() {
		// TODO Auto-generated method stub
		return this.constrName;
	}

	String constrName;

	public boolean equals(AbstractKey key) {
		if (constrName.equals(key.constrName))
			return true;
		else
			return false;
	}

	@Override
	public void addElement(Column col) {
		// TODO Auto-generated method stub
		this.cols.add(col.name);

	}

	@Override
	public void addElement(String cName) {
		// TODO Auto-generated method stub
		this.cols.add(cName);

	}

	@Override
	public void refresh(Table table) {
		// TODO Auto-generated method stub
		return;
	}

}
