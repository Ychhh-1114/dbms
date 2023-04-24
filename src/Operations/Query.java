package Operations;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Elements.DataBase;
import Elements.QueryTable;

public class Query extends AbstractOperation {
	/*
	 * select from where join on group by having order by
	 * 
	 * 
	 */
	public QueryTable viewTable = null;
	public Select viewSelect = null;

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String cNames = (String) args[0];
		String params = "from " + (String) args[1];
		QueryControl control = new QueryControl();
		QueryTable table = null;
		Select select = new Select(cNames);
		try {
			control.getQueryControl("from " + params);
			table = control.launch(select);
		} catch (InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		table.show(select);
	}

	@Test
	public void test() {
		try {
			DataBase.currentUser = Loader.getUser("root", "123");
		} catch (DBMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql[] = { "*", "ychhh join on cqgg" };
		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
