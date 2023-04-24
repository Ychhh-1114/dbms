package Keys;

import Configs.DBMSException;
import Elements.Column;
import Elements.Table;
import Elements.Tuple;

public interface Key {

//	public void refresh();
	public boolean isLegal();

	public boolean isLegal(Table table, Tuple tuple);

	public boolean isLegal(Tuple tuple);

	public Key getKey(Object... args) throws DBMSException;

	public String getKeyName();

	public void addElement(String cName);

	public void addElement(Column col);

	public void refresh(Table table);

	public boolean isColumnIn(String cName);

	public boolean isLegal(Table table, String cName, String value) throws DBMSException;

}
