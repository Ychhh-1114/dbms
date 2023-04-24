package Elements;

import java.util.ArrayList;
import java.util.HashSet;

import Configs.DBMSException;
import Keys.CheckedKey;
import Keys.DefaultKey;
import Keys.ForeignKey;
import Keys.Key;
import Keys.NotnullKey;
import Keys.PrimaryKey;
import Keys.UniqueKey;
import Operations.Where;
import Symbols.EqualsSign;
import Symbols.SymbolTree;
import Type.AbstractType;

public class Table extends AbstractElements {

	private static final long serialVersionUID = 20L;

	public String tName;

	public ArrayList<Tuple> tuples;
	///////////////////////////////////////////
	public HashSet<Column> cols;

	public NotnullKey notnullKey;
	public UniqueKey uniqueKey;
	public DefaultKey defaultKey;
	public CheckedKey checkedKey;
	public ForeignKey foreignKey;
	public PrimaryKey primaryKey;

	public HashSet<Key> keySet = new HashSet<Key>();

	//////////////////////////////////////////////////////////
	public Table(String tName) {
		// TODO Auto-generated constructor stub
		notnullKey = new NotnullKey();
		uniqueKey = new UniqueKey();
		defaultKey = new DefaultKey();
		checkedKey = new CheckedKey();
		foreignKey = new ForeignKey();
		primaryKey = new PrimaryKey();

		tuples = new ArrayList<Tuple>();
		cols = new HashSet<Column>();
		this.tName = tName;
	}

	public Table() {
		// TODO Auto-generated constructor stub

		notnullKey = new NotnullKey();
		uniqueKey = new UniqueKey();
		defaultKey = new DefaultKey();
		checkedKey = new CheckedKey();
		foreignKey = new ForeignKey();
		primaryKey = new PrimaryKey();

		tuples = new ArrayList<Tuple>();
		cols = new HashSet<Column>();
	}

	////////////////////////////////////////////
	public boolean isColumnExist(String cName) throws DBMSException {
		if (cols.contains(new Column(cName)))
			return true;
		else
			return false;

	}

	public boolean areColumnsExist(String cNames[]) {
		for (String str : cNames) {
			if (cols.contains(new Column(str)))
				continue;
			else
				return false;
		}

		return true;
	}
	////////////////////////////////////////////////////

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		for (Column col : cols)
			col.refresh(tuples);

		keySet.add(primaryKey);
		keySet.add(foreignKey);
		keySet.add(uniqueKey);
		keySet.add(notnullKey);
		keySet.add(checkedKey);
		keySet.add(defaultKey);

		for (Key key : keySet)
			if (key != null)
				key.refresh(this);

		// save?
		TableManage.saveTable(this);

	}

	/////////////////////////////////////////////////////
	public boolean isTupleLegal(Tuple tuple) {
		for (Key key : keySet)
			if (key != null)
				if (key.isLegal(this, tuple))
					continue;
				else
					return false;

		ArrayList<Column> cList = new ArrayList<Column>();
		for (Column col : cols)
			cList.add(col);

		for (String str : tuple.value.keySet()) {
			Column col = cList.get(cList.indexOf(new Column(str)));
			if (col.isColumnLegal(tuple.value.get(str)))
				continue;
			else
				return false;

		}

		return true;

	}

	///////////////////////////////////////////////////////////////
	public void insert(Tuple tuple) {
		tuples.add(tuple);
		this.refresh();
	}

	public void deleteTuples(SymbolTree root) {
		HashSet<Tuple> bin = new HashSet();
		for (Tuple tuple : tuples)
			if (root.isTreeLegal(this, tuple))
				bin.add(tuple);
		tuples.removeAll(bin);
		System.out.println("change " + bin.size() + " rows!");
		bin = null;
		this.refresh();
	}

	public int update(Where where, ArrayList<EqualsSign> equals) throws DBMSException {
		int change = 0;
		for (Tuple tuple : tuples) {
			if (where.root.isTreeLegal(this, tuple)) {
				for (EqualsSign equal : equals) {
					String cName = equal.cName;
					String cValue = equal.cValue;
					AbstractType type = null;
					for (Column col : this.cols) {
						if (col.name.equals(cName)) {
							type = col.type;
							break;
						}
					}
					if (type.isLegal(cValue)) {
						for (Key key : keySet) {
							if (key.isColumnIn(cName)) {
								if (key.isLegal(this, cName, cValue)) {
									tuple.value.remove(cName);
									tuple.value.put(cName, cValue);
									change++;
								} else
									throw new DBMSException("the value is illegal");

							}

						}
					} else
						throw new DBMSException("the update value is illegal!");

				}
			}
		}

		this.refresh();
		return change;

	}

}
