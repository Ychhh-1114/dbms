package Keys;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.Column;
import Elements.Table;
import Elements.TableManage;
import Elements.Tuple;

public class ForeignKey extends AbstractKey {

	static public Pattern pattern = Pattern.compile(".*foreign\s+key\s+\\(\\w+\\)\s+reference\s+\\w+.*\\(\\w+\\).*");

	public HashMap<String, String> refmap = new HashMap();

	@Override
	public Key getKey(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		Table table = (Table) args[0];
		String sql = (String) args[1];
		Matcher matcher = pattern.matcher(sql);
		if (matcher.find()) {
			String cName = matcher.group(1);
			String tName = matcher.group(2);
			String tcName = matcher.group(3);
			if (TableManage.isTableHasCol(table, cName) && TableManage.isPrimaryOfTable(tName, tcName)) {
				refmap.put("key", cName);
				refmap.put("table", tName);
				refmap.put("refKey", tcName);
				this.cols.add(cName);
			} else
				throw new DBMSException("table and attribute is illegal!");

//			
		} else
			throw new DBMSException("table and attribute is illegal!");
		return this;

	}

	public ForeignKey() {
		// TODO Auto-generated constructor stub
		this.constrName = "foreignKey";
	}

	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLegal(Table table, Tuple tuple) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLegal(Tuple tuple) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isColumnIn(String cName) {
		// TODO Auto-generated method stub
		if (this.cols.contains(cName))
			return true;
		else
			return false;
	}

	@Override
	public boolean isLegal(Table table, String cName, String value) throws DBMSException {
		// TODO Auto-generated method stub
		return false;
	}

}
