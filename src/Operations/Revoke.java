package Operations;

import java.util.ArrayList;

import Configs.DBMSException;
import Configs.Loader;
import Elements.DataBase;
import Users.User;

public class Revoke extends AbstractOperation {
	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String type = (String) args[0];
		String tables = (String) args[1];
		String users = (String) args[2];
		String user[] = null;
		boolean all = false;
		user = users.split(",");
		String types[] = type.split(",");
		String table[] = tables.split(",");
		for (String tName : table)
			for (String t : types) {
				if (t.trim().equals("all")) {
					all = true;
					if (DataBase.currentUser.privilage.tMP.get(tName).size() < 4)
						throw new DBMSException("has not the pribilege!");
				} else {
					if (!DataBase.currentUser.privilage.isPrivilage(tName, t))
						throw new DBMSException("has not the pribilege!");
				}
			}
		ArrayList<User> uList = new ArrayList();

		for (String uName : user) {
			if (!Loader.isUserExisted(uName))
				throw new DBMSException("has not the pribilege!");
			else
				uList.add(Loader.getUser(uName));
		}

		for (User u : uList)
			if (all) {
				String allType[] = { "select", "update", "insert", "delete" };
				u.revokePrivilege(allType, table);
			} else
				u.revokePrivilege(types, table);
		System.out.println("grant sucessfully!");
	}

}
