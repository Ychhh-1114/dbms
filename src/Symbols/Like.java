package Symbols;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Elements.QueryTable;
import Elements.Table;

public class Like extends AbstractSymbol {
	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");
	public String tName;

	public String toRegex(String value) {
		value = value.replace("_", ".");
		value = value.replace("%", ".*");
		return value;

	}

	@Override
	public boolean isConstrLegal(Table table, String name, String value) {
		// TODO Auto-generated method stub
		this.cValue = this.toRegex(this.cValue);
		Pattern pattern = Pattern.compile(this.cValue);

		Matcher matcher = pattern.matcher(value);
		if (matcher.find())
			return true;
		else
			return false;

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		Matcher matcher = detail.matcher(name);
		if (matcher.find()) {
			this.tName = matcher.group(1);
			this.cName = matcher.group(2);
		} else
			this.cName = name;
	}

	@Override
	public boolean isConstrLegal(QueryTable table) {
		return false;
	}

	@Override
	public String getTname() {
		// TODO Auto-generated method stub
		return this.tName;
	}

}
