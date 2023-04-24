package Elements;

import java.util.ArrayList;
import java.util.HashMap;

import Operations.Select;

public class ConnTuple extends Tuple {
	public HashMap<String, HashMap<String, String>> tcp = new HashMap<String, HashMap<String, String>>();

	public void addValue(String tName, HashMap<String, String> value) {
		this.tcp.put(tName, value);
		return;
	}

	static public ConnTuple updateToConn(Tuple tuple, String tName) {
		ConnTuple t = new ConnTuple();
		t.tcp.put(tName, new HashMap<String, String>());
		for (String col : tuple.value.keySet())
			t.tcp.get(tName).put(col, tuple.value.get(col));
		return t;

	}

	public String getValue(HashMap<String, ArrayList<String>> map) {
		String returnVal = "";
		for (String str : map.keySet()) {
			HashMap<String, String> tmap = this.tcp.get(str);
			for (String col : map.get(str))
				returnVal += tmap.get(col);
		}
		return returnVal;
	}

	public void show(Select select, boolean all) {
		if (all) {
			for (String tName : this.tcp.keySet()) {
				for (String cName : this.tcp.get(tName).keySet()) {
					System.out.print(this.tcp.get(tName).get(cName) + "    ");
				}
			}
			System.out.println();
		} else {
			for (Table table : select.selectmap.keySet()) {
				for (String cName : select.selectmap.get(table)) {
					System.out.print(this.tcp.get(table.tName).get(cName) + "    ");
				}
			}
			System.out.println();
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		ConnTuple tuple = (ConnTuple) obj;
		for (String tName : this.tcp.keySet()) {
			for (String cName : this.tcp.get(tName).keySet()) {
				if (!this.tcp.get(tName).get(cName).equals(tuple.tcp.get(tName).get(cName)))
					return false;
			}
		}
		return true;
	}

}
