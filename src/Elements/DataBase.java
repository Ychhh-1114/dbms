package Elements;

import Users.User;

public class DataBase {

	static public String currentDataBase = "ych";
	static public User currentUser;
//	static public boolean exist = false; //if process get error exist
	static public boolean isRightRe = true;

	static public void refresh() {
		isRightRe = true;
	}

}
