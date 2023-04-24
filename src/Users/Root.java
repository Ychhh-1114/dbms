package Users;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Operations.AbstractOperation;
import Operations.CreateDataBase;
import Operations.DeleteDataBase;

public class Root extends User {
	public Root(String name, String passwd) {
		// TODO Auto-generated constructor stub
		super(name, passwd);
	}

}
