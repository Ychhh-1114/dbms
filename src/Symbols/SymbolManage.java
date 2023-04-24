package Symbols;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.DataBase;

public class SymbolManage {

	static public Pattern greater = Pattern.compile("(.*)\s+>\s+(.*)");
	static public Pattern less = Pattern.compile("(.*)\s+<\s+(.*)");
	static public Pattern equal = Pattern.compile("(.*)\s+=\s+(.*)");
	static public Pattern inequal = Pattern.compile("(.*)\s+<=>\s+(.*)");
	static public Pattern greaterEqual = Pattern.compile("(.*)\s+>=\s+(.*)");
	static public Pattern lessEqual = Pattern.compile("(.*)\s+<=\s+(.*)");
	static public Pattern like = Pattern.compile("(.*)\s+like\s+(.*)");

	static public HashMap<Pattern, Class> symbolmp = new HashMap();
	static {
		symbolmp.put(greater, GreaterThan.class);
		symbolmp.put(less, LessThan.class);
		symbolmp.put(equal, EqualsSign.class);
		symbolmp.put(inequal, InEqualSign.class);
		symbolmp.put(greaterEqual, GreaterAndEqual.class);
		symbolmp.put(lessEqual, LessAndEqual.class);
		symbolmp.put(like, Like.class);

	}

	static public AbstractSymbol getSymbol(String sql)
			throws InstantiationException, IllegalAccessException, DBMSException {
		for (Pattern pattern : symbolmp.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				String cName = matcher.group(1).trim();
				String value = matcher.group(2).trim();
				AbstractSymbol symbol = (AbstractSymbol) symbolmp.get(pattern).newInstance();
				symbol.setName(cName);
				;
				symbol.cValue = value;
				return symbol;
			}

		}
		DataBase.isRightRe = false;
		throw new DBMSException("can't match the compare symbol");
	}

}
