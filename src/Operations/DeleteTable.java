package Operations;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Elements.Table;

public class DeleteTable extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String tName = (String) args[0];
		String param = null;
		if (args.length == 1) {
			if (Loader.isTableExists(tName)) {
				Table table = Loader.getTable(tName);
				DropTable dropTable = new DropTable();
				dropTable.dropTable(table);
				System.out.println("delete sucessfully!");
				return;
			} else
				throw new DBMSException("the table has not existed!");

		}

		param = (String) args[1];
		if (Loader.isTableExists(tName)) {
			Table table = Loader.getTable(tName);
//			if(PrivilegeManage.isPrivilage(tName, "delete")) {
			Where where = new Where(param);
			if (where.isWhereExisted()) {
				where.operate(param);
				table.deleteTuples(where.root);
			} else {
				DropTable dropTable = new DropTable();
				dropTable.dropTable(table);
			}
			System.out.println("delete sucessfully!");
//			}

		} else
			throw new DBMSException("the table has not existed!");

	}

	@Test
	public void test() {
		String sql[] = { "ychhh" };
		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
