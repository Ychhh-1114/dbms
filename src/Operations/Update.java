package Operations;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Elements.Table;
import Elements.Tuple;
import Symbols.EqualsSign;
import Users.PrivilegeManage;

public class Update extends AbstractOperation {

	static private Pattern pattern = Pattern.compile("(.*)\s+=\s+(.*)");

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String tName = (String) args[0];
		String newParams = (String) args[1];
		String constrParams = (String) args[2];

		if (Loader.isTableExists(tName.trim())) {
			Table table = Loader.getTable(tName.trim());
//			if(PrivilegeManage.isPrivilage(tName, "update")) {
			String setParams[] = newParams.split(",");
			ArrayList<EqualsSign> setList = new ArrayList();
			ArrayList<String> cNames = new ArrayList<String>();
			for (String str : setParams) {
				EqualsSign equal = new EqualsSign();
				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					equal.cName = matcher.group(1).trim();
					equal.cValue = matcher.group(2).trim();
					setList.add(equal);
					cNames.add(matcher.group(1));
				} else
					throw new DBMSException("can't match the parameters");
			}
//				if(PrivilegeManage.isPrivilage(tName,cNames, "update")) {
			Where where = new Where();
			where.setData(constrParams);
			where.operate();
			int change = table.update(where, setList);
			System.out.println("the " + change + " rows changed.");
			System.out.println("update sucessfully!");
//				}

//			}
		} else
			throw new DBMSException("the table has not existed!");

	}

	@Test
	public void test() {
		String sql[] = { "ychhh", "name = y", "name = ych" };
		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
