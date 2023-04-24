package Keys;

import java.util.HashSet;

import Configs.DBMSException;
import Elements.Table;
import Elements.Tuple;
import Symbols.Symbol;
import Symbols.SymbolNode;
import Symbols.SymbolTree;

public class CheckedKey extends AbstractKey {

	// AND prior to OR
	// logical prior to Arithmetic

	private String data;
	// name > 6 and name < 7 name > 6 or name < 7

	public SymbolTree root;

	public void processToSymbol() throws InstantiationException, IllegalAccessException, DBMSException { // process //
																											// to
																											// normalized
																											// //
																											// segment
		SymbolTree tree = new SymbolTree();
		root = tree.createTree(data);
	}

	public void setData(String data) throws InstantiationException, IllegalAccessException, DBMSException {
		this.data = data.trim();
		processToSymbol();

	}

	/////////////////////////////////////////////////////////////
	@Override
	public Key getKey(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public CheckedKey() {
		// TODO Auto-generated constructor stub
		this.constrName = "checkedKey";
	}

	///////////////////////////////////////
	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLegal(Table table, Tuple tuple) {
		// TODO Auto-generated method stub
		for (String str : tuple.value.keySet()) {
			if (cols.contains(str)) {
				String cName = str;
				String cValue = tuple.value.get(str).trim();
				try {
					if (root.isCheckedLegal(table, cName, cValue))
						continue;
					else
						return false;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DBMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
		boolean res = false;
		try {
			res = root.isCheckedLegal(table, cName, value);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
