package Users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Configs.DBMSException;

public class Privilage implements Serializable {

	// table map<column,set<operate>>
	public HashMap<String, HashMap<String, HashSet<String>>> pMP = new HashMap<String, HashMap<String, HashSet<String>>>();
	public HashMap<String, HashSet<String>> tMP = new HashMap<String, HashSet<String>>();

	/*
	 * table: 1. create:all operate 2. select 3. insert 4. update 5. delete
	 * 
	 * column: first has table privilege 1. select 2. insert 3. uodate 4. delete
	 * 
	 * 
	 */

	public void addPrivilage(String tName, String operate) {
		if (this.tMP.containsKey(tName)) {
			this.tMP.get(tName).add(operate);
		} else {

			HashSet<String> set = new HashSet<String>();
			set.add(operate);
			this.tMP.put(tName, set);
		}

	}

	public void addPrivilage(String tName, String aName, String operate) {
//		String 

	}

	public boolean isPrivilage(String tName, ArrayList<String> cols, String operate) throws DBMSException {
		if (this.tMP.get(tName).equals("create"))
			return true;
		if (pMP.containsKey(tName)) {

			HashMap<String, HashSet<String>> mp = pMP.get(tName);
			for (int i = 0; i < cols.size(); i++) {
				if (mp.keySet().contains(cols.get(i))) {
					HashSet<String> op = mp.get(cols.get(i));
					if (op.contains(operate))
						continue;
					else
						throw new DBMSException("has not this privilege");
				} else
					throw new DBMSException("has not this attribute");
			}
			return true;
		}
		throw new DBMSException("has not the privillage!");

	}

	public boolean isPrivilage(String tName, String[] aNames, String[] operate) throws DBMSException {
		if (this.tMP.get(tName).equals("create"))
			return true;
		if (pMP.containsKey(tName)) {
			HashMap<String, HashSet<String>> mp = pMP.get(tName);
			for (int i = 0; i < aNames.length; i++) {
				if (mp.keySet().contains(aNames[i])) {
					HashSet<String> op = mp.get(aNames[i]);
					if (op.contains(operate[i]))
						continue;
					else
						throw new DBMSException("has not this privilege");
				} else
					throw new DBMSException("has not this attribute");
			}
			return true;
		}
		throw new DBMSException("has not the privillage!");

	}

	public boolean isPrivilage(String tName, String operate) throws DBMSException {
		if (this.tMP.get(tName).equals("create"))
			return true;
		if (tMP.containsKey(tName)) {
			HashSet op = tMP.get(tName);
			if (op.contains("create") || op.contains(operate))
				return true;
			else
				throw new DBMSException("has not the privillage");
		} else
			throw new DBMSException("has not the privillage");
	}

	public boolean isPrivilage(String tName, String aName, String operate) throws DBMSException {
		if (this.tMP.get(tName).equals("create"))
			return true;
		if (pMP.containsKey(tName)) {
			HashMap<String, HashSet<String>> mp = pMP.get(tName);
			if (mp.containsKey(aName)) {
				HashSet<String> set = mp.get(aName);
				if (set.contains(operate))
					return true;
				else
					throw new DBMSException("has not the privillage");
			} else
				throw new DBMSException("has not the privillage");
		} else
			throw new DBMSException("has not the privillage");
	}

}
