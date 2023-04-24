package Operations;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.lang.reflect.Field;

import Configs.DBMSException;
import Elements.Column;
import Elements.Table;
import Keys.ForeignKey;
import Keys.Key;
import Keys.KeyManage;
import Keys.PrimaryKey;
import Type.AbstractType;
import Type.DBDouble;
import Type.DBInt;
import Type.DBVarchar;

public class Preprocess extends AbstractOperation {

	static private Pattern Varchar = Pattern.compile("\s*(\\w+)\s+varchar\\((\\d+)\\)(.*)\s*");
	static private Pattern Int = Pattern.compile("\s*(\\w+)\s+int(.*)\s*");
	static private Pattern Double = Pattern.compile("\s*(\\w+)\s+double(.*)\s*");

	static private HashMap<Pattern, Class> constrType = new HashMap();
	static {
		constrType.put(Varchar, DBVarchar.class);
		constrType.put(Int, DBInt.class);
		constrType.put(Double, DBDouble.class);

	}

	static public Pattern Foreign = Pattern.compile(".*foreign\s+key\s+\\(\\w+\\)\s+reference\s+\\w+.*\\(\\w+\\).*");
	static public Pattern Primary = Pattern.compile(".*primary\s+key\s*\\((.*)\\)\s*");
//	static public Pattern Unique = Pattern.compile(".*unique\s+\\((.*)\\)\s*");
//	static public Pattern Notnull = Pattern.compile(".*not\s+null\s+\\((.*)\\)\s*");

	static private HashMap<Pattern, Class> tableConstr = new HashMap<Pattern, Class>();
	static {
		constrType.put(Foreign, ForeignKey.class);
		constrType.put(Primary, PrimaryKey.class);
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		Table table = (Table) args[0];
		String sql = (String) args[1];
		for (Pattern pattern : constrType.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				String name = matcher.group(1);
				if (constrType.get(pattern) == DBVarchar.class) {
					String length = matcher.group(2);
					Key key = null;
					Column col = null;
					DBVarchar type = null;

					try {
						key = KeyManage.getKey(matcher.group(3).trim());
						col = new Column(name.trim());
						type = (DBVarchar) constrType.get(pattern).newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					type.length = length;
					col.type = type;

					table.cols.add(col);
					if (key != null) {
						try {
							key.addElement(col); // 将列名添加进标记约束
							Field field = Table.class.getDeclaredField(key.getKeyName());
							field.set(table, key);

						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {

					Key key = null;
					Column col = null;
					col = new Column(name.trim());

					try {
						col.type = (AbstractType) constrType.get(pattern).newInstance();
						key = KeyManage.getKey(matcher.group(2).trim());
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					table.cols.add(col);
					if (key != null) {
						try {
							key.addElement(col); // 列名添加进表级约束
							Field field = Table.class.getDeclaredField(key.getKeyName());
							field.set(table, key);

						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
				return;
			}
		}

		try {
			tableProcess(table, sql);
			return;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new DBMSException("create table failed!");

	}

	private void tableProcess(Table table, String sql) throws DBMSException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		for (Pattern pattern : tableConstr.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				Key key = (Key) tableConstr.get(pattern).newInstance();
				Field field = Table.class.getDeclaredField(key.getKeyName());
				field.set(table, key.getKey(table, sql));
			}
		}
	}

}
