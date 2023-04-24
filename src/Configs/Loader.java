package Configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Elements.Index;
import Elements.Table;
import Elements.View;
import Users.User;

public class Loader {
	static public Table getTable(String tName) {
		Table table = null;
		if (isTableExists(tName)) {

			FileInputStream fis = null;
			ObjectInputStream ois = null;

			try {
				fis = new FileInputStream(Path.tablePath(tName));
				ois = new ObjectInputStream(fis);

				table = (Table) ois.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return table;

	}

	static public boolean isTableExists(String tName) {
		File table = new File(Path.tablePath(tName));
		if (table.exists())
			return true;
		return false;

	}

	static public boolean isDBExists(String dName) {
		File db = new File(Path.dbPath(dName));
		if (db.exists())
			return true;
		else
			return false;

	}

	static public boolean isIndexExisted(String iName) {
		File index = new File(Path.indexPath(iName));
		if (index.exists())
			return true;
		else
			return false;

	}

	static public Index getIndex(String iName) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Index index = null;
		try {
			fis = new FileInputStream(Path.indexPath(iName));
			ois = new ObjectInputStream(fis);
			index = (Index) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
	}

	static public User getUser(String uName) throws DBMSException {
		File file = new File(Path.userPath(uName));
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		User user = null;

		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				user = (User) ois.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else
			throw new DBMSException("has not this user");

		return user;

	}

	static public boolean isUserExisted(String uName) {
		File user = new File(Path.userPath(uName));
		if (user.exists())
			return true;
		else
			return false;
	}

	static public User getUser(String uName, String passwd) throws DBMSException {

		File file = new File(Path.userPath(uName));
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		User user = null;

		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				user = (User) ois.readObject();
				if (!user.passwd.equals(passwd))
					throw new DBMSException("the passwd is worse!!!");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else
			throw new DBMSException("has not this user");

		return user;

	}

	static public boolean isViewExisted(String vName) {
		File view = new File(Path.viewPath(vName));
		if (view.exists())
			return true;
		else
			return false;

	}

	static public View getView(String vName) throws DBMSException {

		View view = null;
		if (isViewExisted(vName)) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(Path.viewPath(vName));
				ois = new ObjectInputStream(fis);
				view = (View) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return view;

		} else
			throw new DBMSException("the view has not existed!");
	}

}
