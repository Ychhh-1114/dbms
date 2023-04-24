package Type;

public class DBVarchar extends AbstractType {

	public String value;
	public String length;

	public DBVarchar(String length) {
		// TODO Auto-generated constructor stub
		this.length = length;
	}

	public DBVarchar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLegal(String value) {
		// TODO Auto-generated method stub
		if (value.length() <= Integer.parseInt(length))
			return true;
		else
			return false;
	}

	@Override
	public void setValue(String v) {
		// TODO Auto-generated method stub
		this.value = v;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		DBVarchar var = (DBVarchar) o;
		return this.value.compareTo(var.value);
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public String getValue(String val) {
		// TODO Auto-generated method stub
		return val;
	}

	static public String valueOf(String val) {
		return val;

	}

}
