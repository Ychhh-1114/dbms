package Main;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Configs.Loader;
import Elements.DataBase;
import Users.Root;
import Users.User;
import Users.UserManage;

public class Client {

	static public Pattern loginOn = Pattern.compile("\s*ychsql\s+-u\s+(\\w+)\s+-p\s+(.*)");
	static public Scanner sc = new Scanner(System.in);

	static public void launch() throws DBMSException {

		while (true) {
			System.out.println("please login on first: ");
			String logon = sc.nextLine();
			Matcher matcher = loginOn.matcher(logon);
			if (matcher.find()) {

				String uName = matcher.group(1).trim();
				String passwd = matcher.group(2).trim();
				if (Loader.isUserExisted(uName)) {
					User user = Loader.getUser(uName, passwd);
					DataBase.currentUser = user;
					System.out.println("user loginOn sucessfully!");
					return;
				} else {
					System.out.println("has not the user!");
					break;
				}

			}
		}
	}

	static public void main(String[] args)
			throws DBMSException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		launch();
		while (true) {
			String sql = sc.nextLine();
			Meditor.operation(sql);

		}

	}

}
