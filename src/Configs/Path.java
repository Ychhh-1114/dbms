package Configs;

import Elements.DataBase;

public enum Path {

	dataBase("./databases/"), user("./users/"), view("./views/"), index("./indexes/");

	private String path;

	private Path(String path) {
		this.path = path;
	}

	public String toString() {
		return path;
	}

	///////////////////////////////////////////

	static public String indexPath(String iName) {
		return index + iName;

	}

	static public String viewPath(String vName) {
		return view + vName;
	}

	static public String tablePath(String tName) {
		return dataBase + DataBase.currentDataBase + "/" + tName;

	}

	static public String userPath(String uName) {
		return user + uName;

	}

	static public String dbPath(String dName) {
		return dataBase + dName;
	}

}
