package Users;

import java.util.ArrayList;
import java.util.HashSet;

import Configs.DBMSException;
import Elements.DataBase;
import Elements.Table;
import Operations.Select;

public class PrivilegeManage {

//	static public boolean isPrivilege(Table table,String []aNames) {
//		if(DataBase.currentUser instanceof Root)return true;
//		else return DataBase.currentUser.privilage.
//	}

	static public boolean isPrivilage(Select select) throws DBMSException {
		if (DataBase.currentUser instanceof Root)
			return true;
		else {
			if (select.isAll) {
				for (Table table : select.selectmap.keySet()) {
					if (DataBase.currentUser.privilage.isPrivilage(table.tName, "select"))
						continue;
					else
						return false;
				}
				return true;
			} else {
				for (Table table : select.selectmap.keySet()) {
					HashSet<String> set = select.selectmap.get(table);
					for (String col : set)
						if (DataBase.currentUser.privilage.isPrivilage(table.tName, col))
							continue;
						else
							return false;
				}
				return true;
			}
		}

	}

	static public boolean isPrivilage(String tName, String aName, String operate) throws DBMSException {
		if (DataBase.currentUser instanceof Root)
			return true;
		else
			return DataBase.currentUser.privilage.isPrivilage(tName, operate);
	}

	static public boolean isPrivilage(String tName, String operate) throws DBMSException {
		if (DataBase.currentUser instanceof Root)
			return true;
		else
			return DataBase.currentUser.privilage.isPrivilage(tName, operate);
	}

//	static public void addPrivilage(String tName,String aName,String operate) throws DBMSException {
//		if(DataBase.currentUser instanceof Root) 
//			throw new DBMSException("current user is Root,no need add privilege!");
//		else {
//			String params[] = (String[])args;
//			for(String str : params)
//				if(DataBase.currentUser.privilage.)
//		}
//			
//	}

	static public void addPrivilage(String tName, String aName[], String operate[]) throws DBMSException {

	}

	static public boolean isPrivilage(String tName, ArrayList<String> cols, String operate) throws DBMSException {
		if (DataBase.currentUser instanceof Root)
			return true;
		else
			return DataBase.currentUser.privilage.isPrivilage(tName, cols, operate);

	}

}
