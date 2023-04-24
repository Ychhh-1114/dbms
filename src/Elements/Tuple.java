package Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class Tuple extends AbstractElements implements Cloneable {
	private static final long serialVersionUID = 22L;
	public HashMap<String, String> value;

	public Tuple() {
		value = new HashMap<String, String>();
	}

	public Tuple(String attrs[], String values[]) {
		value = new HashMap<String, String>();
		for (int i = 0; i < attrs.length; i++)
			value.put(attrs[i], values[i]);
	}

	public Tuple(String attr, String v) {
		value = new HashMap<String, String>();
		value.put(attr, v);

	}

	///////////////////////////////////////////////
	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	public boolean equals(Object obj) {
		Tuple tuple = (Tuple) obj;
		return value.equals(tuple.value);

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Tuple tuple = new Tuple();
		tuple.value = (HashMap<String, String>) tuple.clone();
		return tuple;
	}

	public String getValue(String[] cList) {
		String val = "";
		for (String c : cList)
			val += this.value.get(c);
		return val;

	}

}
