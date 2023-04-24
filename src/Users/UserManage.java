package Users;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Configs.DBMSException;
import Configs.Loader;
import Configs.Path;

public class UserManage implements Serializable {

	static public void saveUser(User user) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(Path.userPath(user.name));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static public void createUser(String uName, String passwd) throws DBMSException {
		if (Loader.isUserExisted(uName)) {
			User user = new User(uName, passwd);
			saveUser(user);
			System.out.println("create User sucessfully!");
		} else
			throw new DBMSException("the user has existed!");
	}

	static public void deleteUser(String uName) throws DBMSException {
		if (Loader.isUserExisted(uName)) {
			File user = new File(uName);
			user.delete();
			System.out.println("user delete sucessfully!");
		} else
			throw new DBMSException("the user has not existed!");
	}

}
