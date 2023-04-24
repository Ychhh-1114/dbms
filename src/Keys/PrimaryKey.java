package Keys;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.Column;
import Elements.Table;
import Elements.TableManage;
import Elements.Tuple;

public class PrimaryKey extends AbstractKey {

	static private Pattern pattern = Pattern.compile(".*primary\s+key\s*\\((.*)\\).*");

	@Override
	public String getKeyName() {
		// TODO Auto-generated method stub
		return this.constrName;
	}

	public PrimaryKey() {
		// TODO Auto-generated constructor stub
		this.constrName = "primaryKey";
		this.cols = new HashSet<String>();
	}

	@Override
	public Key getKey(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		Table table = (Table) args[0];
		String sql = (String) args[1];
		Matcher matcher = pattern.matcher(sql);
		if (matcher.find()) {
			String values[] = matcher.group(1).split(",");
			for (String str : values)
				str = str.trim();
			if (TableManage.isTableHasCol(table, values)) {
				for (String str : values)
					cols.add(str);
			} else
				throw new DBMSException("values is illegal!");
		} else
			throw new DBMSException("grammar is illegal");
		return this;

	}

	@Override
	public boolean isLegal(Table table, Tuple tuple) {
		// TODO Auto-generated method stub
		for (String str : cols)
			if (tuple.value.containsKey(str)) {
				Column col = new Column(str);
				for (Column c : table.cols)
					if (col.equals(c)) {
						col = c;
						break;
					}
				if (col.value.contains(tuple.value.get(str)))
					return false;
			} else
				return false;

		return true;
	}

	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLegal(Tuple tuple) {
		// TODO Auto-generated method stub
		return false;
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
		for (Column col : table.cols) {
			if (col.name.equals(cName)) {
				if (col.value.contains(value))
					return false;
				else
					return true;
			}
		}
		throw new DBMSException("has not the column");
	}

}
