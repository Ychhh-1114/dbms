package Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Index implements Serializable {

	public ArrayList<String> cNames = new ArrayList<String>();
	public HashMap<String, Tuple> map = new HashMap();
	public String tName;
	public String iName;

	public Index(String tName, String iName, String[] cArr) {
		// TODO Auto-generated constructor stub
		this.tName = tName;
		this.iName = iName;
		for (String c : cArr)
			cNames.add(c);
	}

}
