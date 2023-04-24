package AggregateFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.Column;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;
import Type.AbstractType;

public class FuncManage {
	static private Pattern maxPattern = Pattern.compile(".*max\s*\\((.*)\\)\s*");
	static private Pattern minPattern = Pattern.compile(".*min\s*\\((.*)\\)\s*");
	static private Pattern cPattern = Pattern.compile(".*count\s*\\((.*)\\)\s*");
	static private Pattern sPattern = Pattern.compile(".*sum\s*\\((.*)\\)\s*");

	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");
	static private HashMap<Pattern, Class> funcmap = new HashMap();
	static {
		funcmap.put(maxPattern, Max.class);
		funcmap.put(minPattern, Min.class);
		funcmap.put(cPattern, Count.class);
		funcmap.put(sPattern, Sum.class);

	}

	static public boolean isQueryFunc(String sql) {
		for (Pattern pattern : funcmap.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find())
				return true;
		}
		return false;
	}

	static private boolean isLegal(String sql, QueryTable table) {
		for (String str : table.tcp.keySet()) {
			HashSet<String> cols = (HashSet<String>) table.tcp.get(str).keySet();
			if (cols.contains(sql))
				return true;
		}
		return false;
	}

	static public Function getFunc(String sql, QueryTable table)
			throws InstantiationException, IllegalAccessException, DBMSException {
		for (Pattern pattern : funcmap.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				String cName = matcher.group(1);
				if (isLegal(cName, table)) {
					Function func = (Function) funcmap.get(pattern).newInstance();
					AbstractType type = null;
					for (String tName : table.tcp.keySet()) {
						if (table.tcp.get(tName).keySet().contains(cName)) {
							type = table.tcp.get(tName).get(cName);
						}
					}
					if (type == null) {
						DataBase.isRightRe = false;
						throw new DBMSException("has not the column!");
					}
					func.setName(cName);
					func.setType(type);
					return func;
				}
			}
		}
		DataBase.isRightRe = false;
		throw new DBMSException("can't match the compare function! ");
	}

	static public Function getFunc(String sql, Set<Table> set)
			throws InstantiationException, IllegalAccessException, DBMSException {

		ArrayList<Table> tList = new ArrayList();
		tList.addAll(set);

		for (Pattern pattern : funcmap.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				String param = matcher.group(1);
				Matcher m = detail.matcher(param);
				String tName = null;
				String cName = null;
				if (m.find()) {
					tName = m.group(1).trim();
					cName = m.group(2).trim();
					Table table = tList.get(tList.indexOf(new Table(cName)));
					if (table == null) {
						DataBase.isRightRe = false;
						throw new DBMSException("has not this table");
					}

					if (isLegal(cName, table)) {
						Function func = (Function) funcmap.get(pattern).newInstance();
						func.setName(tName, cName);
						AbstractType type = null;
						for (Column c : table.cols)
							if (c.name.equals(cName)) {
								type = c.type;
								break;
							}
						func.setType(type);
						return func;
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the table has not the column");
					}
				} else {
					cName = param;
					Table table = tList.get(0);

					if (isLegal(cName, table)) {
						Function func = (Function) funcmap.get(pattern).newInstance();
						func.setName(table.tName, cName);
						AbstractType type = null;
						for (Column c : table.cols)
							if (c.name.equals(cName)) {
								type = c.type;
								break;
							}
						func.setType(type);
						return func;
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the table has not the column");
					}
				}

			}
		}
		return null;

	}

	static public Function getFunc(String sql, Table table)
			throws InstantiationException, IllegalAccessException, DBMSException {
		for (Pattern pattern : funcmap.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				String param = matcher.group(1);
				Matcher m = detail.matcher(param);
				String tName = null;
				String cName = null;
				if (m.find()) {
					tName = m.group(1).trim();
					cName = m.group(2).trim();
					if (isLegal(cName, table)) {
						Function func = (Function) funcmap.get(pattern).newInstance();
						func.setName(tName, cName);
						AbstractType type = null;
						for (Column c : table.cols)
							if (c.name.equals(cName)) {
								type = c.type;
								break;
							}
						func.setType(type);
						return func;
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the table has not the column");
					}
				} else {
					cName = param;
					if (isLegal(cName, table)) {
						Function func = (Function) funcmap.get(pattern).newInstance();
						func.setName(table.tName, cName);
						AbstractType type = null;
						for (Column c : table.cols)
							if (c.name.equals(cName)) {
								type = c.type;
								break;
							}
						func.setType(type);
						return func;
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the table has not the column");
					}
				}

			}
		}
		return null;

	}

	static public boolean isLegal(String cName, Table table) {
		System.out.println(table);
		for (Column col : table.cols)
			if (col.name.equals(cName))
				return true;
		return false;
	}

}
