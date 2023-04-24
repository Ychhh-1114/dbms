package Elements;

import java.io.Serializable;
import java.util.ArrayList;

import AggregateFunction.Function;
import Keys.Key;
import Type.AbstractType;

public class Column extends AbstractElements {
	private static final long serialVersionUID = 21L;
	public String name;
	public AbstractType type;
	public ArrayList<String> value;
	public Key key;

	public ArrayList<Function> funcs = new ArrayList();

	public Column() {
		// TODO Auto-generated constructor stub
		value = new ArrayList<String>();

	}

	public Column(String name) {
		// TODO Auto-generated constructor stub
		value = new ArrayList<String>();
		this.name = name;

	}

	public void constrCol(AbstractType abstractType, String name) {
		this.type = abstractType;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	////////////////////////////////////////////////////
	@Override
	public void refresh() {

		// TODO Auto-generated method stub

	}

	public void refresh(ArrayList<Tuple> tuples) {
		value.clear();
		for (Tuple t : tuples)
			if (t.value.containsKey(name)) {
				if (!value.contains(t.value.get(name)))
					value.add(t.value.get(name));
			}

	}

	/////////////////////////////////////////////////////
	public boolean isColumnLegal(String v) {
		return type.isLegal(v);
	}

	//////////////////////////////////////////////////////////

}
