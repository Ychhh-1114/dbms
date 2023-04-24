package Operations;

import Configs.DBMSException;

public abstract class AbstractOperation {

	public abstract void operate(Object... args) throws DBMSException;

}
