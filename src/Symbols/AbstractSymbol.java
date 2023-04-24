package Symbols;

import java.io.Serializable;

import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;

public abstract class AbstractSymbol implements Symbol, Serializable {
	private static final long serialVersionUID = 8L;
	public String symbol;

	public AbstractType type;
	public String cName;
	public String cValue;

	abstract public boolean isConstrLegal(Table table, String name, String value);

	abstract public boolean isConstrLegal(QueryTable table);
}
