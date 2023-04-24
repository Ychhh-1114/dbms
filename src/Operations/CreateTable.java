package Operations;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Elements.DataBase;
import Elements.Table;
import Elements.TableManage;

public class CreateTable extends AbstractOperation {

	public boolean isGrammarLegal(String tName) throws DBMSException {
		if (!Loader.isTableExists(tName))
			return true;
		else
			throw new DBMSException("the table has existed!");

	}

	// create table ych (name varchar(20) primary key,id int,foreign key (id)
	// reference cq(name))
	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String tName = (String) args[0];
		String params = (String) args[1];
		if (isGrammarLegal(tName)) {
			Table table = new Table(tName);
			String sqls[] = params.split(",");
			Preprocess preprocess = new Preprocess();
			for (String sql : sqls)
				preprocess.operate(table, sql);
//			DataBase.currentUser.privilage.addPrivilage(tName, "create");
			TableManage.saveTable(table);
			System.out.println("create table sucessfully!");
		}
	}

	@Test
	public void test() {
		String sql[] = { "ychhh", "name varchar(20) primary key" };
		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
//	public void test() {
//		String sql[] = {"cqgg","id int checked(id > 5)"};
//		try {
//			operate(sql);
//		} catch (DBMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
