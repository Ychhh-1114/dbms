package Symbols;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.ConnTuple;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;
import Elements.Tuple;

public class SymbolNode implements Symbol, Serializable {
	private static final long serialVersionUID = 10L;
	static public boolean legal = true; // 约束合法性
	static public boolean end = false; // 遍历结束
	static public boolean find = false;

	static public Pattern andRegex = Pattern.compile("(.*)\s+and\s+(.*)");
	static public Pattern orRegex = Pattern.compile("(.*)\s+or\s+(.*)");
	static public HashMap<Pattern, Class> nodeCreater = new HashMap();
	static {
		nodeCreater.put(andRegex, And.class);
		nodeCreater.put(orRegex, Or.class);
	}

	public String symbol;

	public SymbolNode left = null;
	public SymbolNode right = null;
	public SymbolNode type = null;
	public String data;

	public AbstractSymbol compData;

	public SymbolNode(String sql) {
		// TODO Auto-generated constructor stub

		this.data = sql;
	}

	public SymbolNode() {
		// TODO Auto-generated constructor stub

	}

	/////////////////////////////////////////////////////////////////////////////

	public void createNode() throws InstantiationException, IllegalAccessException, DBMSException {
		for (Pattern pattern : nodeCreater.keySet()) {
			Matcher matcher = pattern.matcher(data);
			if (matcher.find()) {
				this.type = (SymbolNode) nodeCreater.get(pattern).newInstance();
				this.left = new SymbolNode(matcher.group(1).trim());
				this.right = new SymbolNode(matcher.group(2).trim());
				this.left.createNode();
				this.right.createNode();
				return;
			}
		}

		compDataProduce();

	}

	public void compDataProduce() throws InstantiationException, IllegalAccessException, DBMSException {
		compData = SymbolManage.getSymbol(data);
	}

	/*
	 * 1.找到不对，对于and错，对于or 2.找到，继续找 3.找不到，继续找
	 * 
	 * 
	 */
	// 重新遍历，

	public boolean isValueLegal(QueryTable table) {
		if (left == null && right == null) {
			return this.compData.isConstrLegal(table);
		} else {
			boolean vleft = true, vright = true;
			if (left != null)
				vleft = this.left.isValueLegal(table);
			if (right != null)
				vright = this.right.isValueLegal(table);
			if (type instanceof And) {
				if (vleft && vright)
					return true;
				else
					return false;
			} else {
				if (vleft || vright)
					return true;
				else
					return false;
			}

		}

	}

	public boolean isValueLegal(QueryTable table, ConnTuple tuple) throws DBMSException {

		if (left == null && right == null) {
			String name = compData.cName;
			boolean findc = false;
			String value = null;
			if (compData.getTname() != null)
				value = tuple.tcp.get(compData.getTname()).get(compData.cName);
			else {

				for (String tname : tuple.tcp.keySet()) {
					HashMap<String, String> map = tuple.tcp.get(tname);
					if (map.containsKey(name)) {
						findc = true;
						value = map.get(compData.cName);
						break;
					}
				}
			}

			if (!findc) {
				DataBase.isRightRe = false;
				throw new DBMSException("table has not the column");
			}
			if (value == null)
				return false;
			else
				return compData.isConstrLegal(table, name, value);
		} else {
			boolean vleft = true, vright = true;
			if (left != null)
				vleft = this.left.isValueLegal(table, tuple);
			if (right != null)
				vright = this.right.isValueLegal(table, tuple);
			if (type instanceof And) {
				if (vleft && vright)
					return true;
				else
					return false;
			} else {
				if (vleft || vright)
					return true;
				else
					return false;
			}

		}

	}

	public boolean isValueLegal(Table table, Tuple tuple) {
		if (left == null && right == null) {
			String name = compData.cName;
			String value = tuple.value.get(name);
			if (value == null)
				return true;
			else
				return compData.isConstrLegal(table, name, value);
		} else {
			boolean vleft = true, vright = true;
			if (left != null)
				vleft = this.left.isValueLegal(table, tuple);
			if (right != null)
				vright = this.right.isValueLegal(table, tuple);
			if (type instanceof And) {
				if (vleft && vright)
					return true;
				else
					return false;
			} else {
				if (vleft || vright)
					return true;
				else
					return false;
			}

		}

	}

	public boolean isCheckedLegal(Table table, String cName, String cValue)
			throws InstantiationException, IllegalAccessException, DBMSException {
		return compData.isConstrLegal(table, cName, cValue);
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTname() {
		// TODO Auto-generated method stub
		return null;
	}

}
