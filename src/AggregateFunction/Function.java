package AggregateFunction;

import Configs.DBMSException;
import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;

public interface Function {
	public String toString();

	public void setName(String tName, String cName);

	public void getValue(Table table) throws DBMSException;

	public void setName(String funcName);

	public String getValue(QueryTable table);

	public void setType(AbstractType type);

	public AbstractType getType();

	public String getName();

}
