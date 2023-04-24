package Operations;

import java.util.HashMap;

import Configs.DBMSException;

public class ConnWhere extends AbstractOperation {

	public String data;

	// <table1, column1> <table2, column2>
	public HashMap<HashMap<String, String>, HashMap<String, String>> conn = new HashMap();

	public ConnWhere(String data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub

	}

}
