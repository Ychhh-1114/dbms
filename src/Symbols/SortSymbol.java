package Symbols;

import Elements.QueryTable;

public interface SortSymbol {

	public void sort(QueryTable table);

	public void setName(String name);

	public void sortTables(QueryTable table);

}
