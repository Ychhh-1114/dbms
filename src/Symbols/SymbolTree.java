package Symbols;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.ConnTuple;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;
import Elements.Tuple;

public class SymbolTree extends SymbolNode {

	// and or tree/
	// left -> and
	// right -> or

	// a > 10 or b < 10
	static public Pattern andRegex = Pattern.compile("(.*)\s+and\s+(.*)");
	static public Pattern orRegex = Pattern.compile("(.*)\s+or\s+(.*)");
	static public HashMap<Pattern, Class> nodeCreater = new HashMap();
	static {
		nodeCreater.put(andRegex, And.class);
		nodeCreater.put(orRegex, Or.class);
	}

	public boolean isCheckedLegal(Table table, String cName, String cValue)
			throws InstantiationException, IllegalAccessException, DBMSException {
		if (type instanceof And) {
			if (this.left.isCheckedLegal(table, cName, cValue) && this.right.isCheckedLegal(table, cName, cValue))
				return true;
			else
				return false;
		} else if (type instanceof Or) {
			if (this.left.isCheckedLegal(table, cName, cValue) || this.right.isCheckedLegal(table, cName, cValue))
				return true;
			else
				return false;
		} else {
			return this.left.isCheckedLegal(table, cName, cValue);
		}
	}

	public boolean isTreeLegal(QueryTable table, ConnTuple tuple) throws DBMSException {
		boolean vl = true, vr = true;
		if (left != null)
			vl = this.left.isValueLegal(table, tuple);
		if (right != null)
			vr = this.right.isValueLegal(table, tuple);
		if (type instanceof And) {
			if (vl && vr)
				return true;
			else
				return false;
		} else if (type instanceof Or) {
			if (vl || vr)
				return true;
			else
				return false;
		} else {
			return this.left.isValueLegal(table, tuple);
		}
	}

	public boolean isTreeLegal(Table table, Tuple tuple) {
		boolean vl = true, vr = true;
		if (left != null)
			vl = this.left.isValueLegal(table, tuple);
		if (right != null)
			vr = this.right.isValueLegal(table, tuple);
		if (type instanceof And) {
			if (vl && vr)
				return true;
			else
				return false;
		} else if (type instanceof Or) {
			if (vl || vr)
				return true;
			else
				return false;
		} else {
			return this.left.isValueLegal(table, tuple);
		}
	}

	// a and b and c and d
	public SymbolTree createTree(String sql) throws InstantiationException, IllegalAccessException, DBMSException {

		for (Pattern pattern : nodeCreater.keySet()) {
			Matcher matcher = pattern.matcher(sql);
			if (matcher.find()) {
				this.type = (SymbolNode) nodeCreater.get(pattern).newInstance();
				this.left = new SymbolNode(matcher.group(1).trim());
				this.right = new SymbolNode(matcher.group(2).trim());
				this.left.createNode();
				this.right.createNode();
				return this;
			}
		}

		if (this.left == null && this.right == null) {
			this.type = new And();
			this.left = new SymbolNode(sql.trim());
			this.left.createNode();
			return this;

		} else {
			DataBase.isRightRe = false;
			throw new DBMSException("can't match the compare symbol");
		}

	}

}
