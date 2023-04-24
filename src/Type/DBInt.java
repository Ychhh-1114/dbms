package Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBInt extends AbstractType {

	static private Pattern pattern = Pattern.compile("\s*\\d+\s*");

	public int ivalue;

	@Override
	public boolean isLegal(String value) {
		// TODO Auto-generated method stub
		Matcher matcher = pattern.matcher(value);
		if (matcher.find())
			return true;
		else
			return false;
	}

	@Override
	public void setValue(String v) {
		// TODO Auto-generated method stub
		ivalue = Integer.parseInt(v);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		DBInt dbi = (DBInt) o;
		if (this.ivalue == dbi.ivalue)
			return 0;
		else if (this.ivalue > dbi.ivalue)
			return 1;
		else
			return -1;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return String.valueOf(this.ivalue);
	}

	@Override
	public Double getValue(String val) {
		// TODO Auto-generated method stub
		return new Double(Double.parseDouble(val));
	}

	static public Integer valueOf(String val) {
		return new Integer(Integer.parseInt(val));

	}

}
