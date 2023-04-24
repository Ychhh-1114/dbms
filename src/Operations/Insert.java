package Operations;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Elements.Table;
import Elements.Tuple;
import Users.PrivilegeManage;

public class Insert extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String tName = (String) args[0];
		String attrs[] = ((String) args[1]).split(",");
		String values[] = ((String) args[2]).split(",");

		for (String str : attrs)
			str = str.trim();
		for (String str : values)
			str = str.trim();

		if (Loader.isTableExists(tName)) {

			Table table = Loader.getTable(tName);
			// if(PrivilegeManage.isPrivilage(tName,"insert")) {
			if (table.areColumnsExist(attrs)) {
				for (int i = 0; i < attrs.length; i++) {
					Tuple tuple = new Tuple(attrs[i], values[i]);
					if (table.isTupleLegal(tuple))
						table.insert(tuple);
					else
						throw new DBMSException("tuple is illegal!");
				}
				System.out.println("insert sucessfully!");
			}

			// }
		} else
			throw new DBMSException("the table has not existed!");

	}

	@Test
	public void test() {
		String sql[] = { "ychhh", "name", "cqgg" };
		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
