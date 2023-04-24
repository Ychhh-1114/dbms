package Operations;

import java.io.File;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Configs.Path;
import Elements.Table;

public class DropTable extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String tName = (String) args[0];
		if (Loader.isTableExists(tName)) {
//			if(PrivilegeManage.isPrivilage(tName, "create")) {
			File table = new File(Path.tablePath(tName));
			table.delete();
			System.out.println("delete table sucessfully!");
//			}

		} else
			throw new DBMSException("the table has not existed!");
	}

	// 无需确认权限直接删除
	public void dropTable(Table table) {
		File file = new File(Path.tablePath(table.tName));
		file.delete();
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
