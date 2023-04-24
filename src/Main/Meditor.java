package Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Configs.DefineMethod;
import Elements.DataBase;
import Users.Root;
import Users.User;
import Users.UserManage;

public class Meditor {

	static private HashMap<Pattern, DefineMethod> meditor = new HashMap<Pattern, DefineMethod>();

	////////// DDL /////////////////
	static private Pattern cdb = Pattern.compile("\s*create\s+database\s+(\\w+)\s*");
	static private Pattern ddb = Pattern.compile("\s*delete\s+database\s+(\\w+)\\s*");
	static private Pattern cd = Pattern.compile("\s*use\s+(.*)\s*");
	static {
		try {
			Method method = User.class.getDeclaredMethod("createDB", String[].class);
			meditor.put(cdb, new DefineMethod(method, 1));

			method = User.class.getDeclaredMethod("deleteDB", String[].class);
			meditor.put(ddb, new DefineMethod(method, 1));

			method = User.class.getDeclaredMethod("changeDB", String[].class);
			meditor.put(cd, new DefineMethod(method, 1));

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	////////// DML /////////////////

	static private Pattern drt = Pattern.compile("\s*drop\s+table\s+(\\w+)\s*");
	static private Pattern ct = Pattern.compile("\s*create\s+table\s+(\\w+)\s+\\((.*)\\)");
	static private Pattern dt = Pattern.compile("\s*delete\s+from\s+(\\w+)\s+(.*)\s*");
	static private Pattern it = Pattern
			.compile("\s*insert\s+into\s+table\s+(\\w+)\s+(.*)\s+values\s+\\((.*)\\)\s*");
	static private Pattern at = Pattern.compile("\s*alter\s+table\s+(\\w+)\s+(\\w+)\s+column\s+(.*)\s*");
	static private Pattern ut = Pattern.compile("\s*update\s+(\\w+)\s+set\s+(.*)\s+where\s+(.*)\s*");

	static {
		try {

			Method method = User.class.getDeclaredMethod("dropTable", String[].class);
			meditor.put(drt, new DefineMethod(method, 1));

			method = User.class.getDeclaredMethod("createTable", String[].class);
			meditor.put(ct, new DefineMethod(method, 2));

			method = User.class.getDeclaredMethod("deleteTable", String[].class);
			meditor.put(dt, new DefineMethod(method, 2));

			method = User.class.getDeclaredMethod("insert", String[].class);
			meditor.put(it, new DefineMethod(method, 3));

			method = User.class.getDeclaredMethod("updateTable", String[].class);
			meditor.put(ut, new DefineMethod(method, 3));

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	////////// DQL /////////////////
	static private Pattern st = Pattern.compile("\s*select\s+(.*)\s+from\s+(.*)\s*");

	static {
		try {
			Method method = User.class.getDeclaredMethod("query", String[].class);
			meditor.put(st, new DefineMethod(method, 2));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	////////// USER /////////////////
	static private Pattern cu = Pattern.compile("\s*create\s+user\s+(.*)\s+passwd\s+(.*)");
	static private Pattern du = Pattern.compile("\s*delete\s+user\s+(.*)\s*");
	static private Pattern gu = Pattern.compile("\s*grant\s+(.*)\s+on\s+(.*)\s+to\s+(.*)\s*");
	static private Pattern ru = Pattern.compile("\s*revoke\s+(.*)\s+on\s+(.*)\s+from\s+(.*)\s*");

	static {
		try {
			Method method = User.class.getDeclaredMethod("createUser", String[].class);
			meditor.put(cu, new DefineMethod(method, 2));

			method = User.class.getDeclaredMethod("deleteUser", String[].class);
			meditor.put(du, new DefineMethod(method, 1));

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static private Pattern cv = Pattern.compile("\s*create\s+view\s+(.*)\s+as\s+(.*)");
	static private Pattern dv = Pattern.compile("\s*delete\s+view\s+(.*)\s*");
	static {
		try {
			Method method = User.class.getDeclaredMethod("createView", String[].class);
			meditor.put(cv, new DefineMethod(method, 2));

			method = User.class.getDeclaredMethod("deleteView", String[].class);
			meditor.put(cv, new DefineMethod(method, 1));

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	///////////////////////////////////////////////////

	static private Pattern ci = Pattern.compile("\s*create\s+index\s+(\\w+)\s+on\s+(\\w+)\s+\\((.*)\\)");
	static private Pattern di = Pattern.compile("\s*delete\s+index\s+(\\w+)\s*");
	static {
		try {
			Method method = User.class.getDeclaredMethod("createIndex", String[].class);
			meditor.put(ci, new DefineMethod(method, 3));

			method = User.class.getDeclaredMethod("deleteIndex", String[].class);
			meditor.put(di, new DefineMethod(method, 1));

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static private Pattern menu = Pattern.compile("\s*show\s+(\\w+)\s*");
	static {
		try {
			Method method = User.class.getDeclaredMethod("show", String[].class);
			meditor.put(menu, new DefineMethod(method, 1));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	static public void operation(String sql)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, DBMSException {
		for (Pattern p : meditor.keySet()) {
			Matcher matcher = p.matcher(sql);
			if (matcher.find()) {
				int params_num = meditor.get(p).param;
				String args[] = new String[params_num];
				for (int i = 0; i < params_num; i++)
					args[i] = matcher.group(i + 1);

				try {
					meditor.get(p).method.invoke(DataBase.currentUser, (Object) args);

				} catch (Exception exception) {
					exception.printStackTrace();
				}

				return;
			}
		}
		throw new DBMSException("has not this op eration!");
	}

}
