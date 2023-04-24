package Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBDouble extends AbstractType {

	static private Pattern minusFloat = Pattern
			.compile("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$");
	static private Pattern positFloat = Pattern.compile("^\\d+(\\.\\d+)?$");

	public double dbvalue;

	public DBDouble() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLegal(String value) {
		// TODO Auto-generated method stub

		if (value.contains("-")) {
			Matcher matcher = minusFloat.matcher(value);
			if (matcher.find())
				return true;
			else
				return false;
		} else {
			Matcher matcher = positFloat.matcher(value);
			if (matcher.find())
				return true;
			else
				return false;
		}

	}

	public DBDouble(double value) {
		// TODO Auto-generated constructor stub
		this.dbvalue = dbvalue;
	}

	public void setValue(String v) {
		this.dbvalue = Double.parseDouble(v);
	}

	@Override
	public int compareTo(Object o) {
		DBDouble dbv = (DBDouble) o;
		if (dbvalue == dbv.dbvalue)
			return 0;
		else if (dbvalue > dbv.dbvalue)
			return 1;
		else
			return -1;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return String.valueOf(this.dbvalue);
	}

	@Override
	public Double getValue(String val) {
		// TODO Auto-generated method stub
		return new Double(Double.valueOf(val));
	}

	static public Double valueOf(String val) {
		return new Double(Double.valueOf(val));
	}

}
