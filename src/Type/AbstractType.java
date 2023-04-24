package Type;

import java.io.Serializable;

public abstract class AbstractType implements Serializable, Comparable {

	private static final long serialVersionUID = 6L;

	abstract public String getValue();

	abstract public void setValue(String v);

	abstract public boolean isLegal(String value);

	abstract public Object getValue(String val);

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
