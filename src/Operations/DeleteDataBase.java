package Operations;

import java.io.File;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Configs.Path;

public class DeleteDataBase extends AbstractOperation {

	public boolean isGrammarLegal(String dName) throws DBMSException {
		// TODO Auto-generated method stub
		if (Loader.isDBExists(dName))
			return true;
		else
			throw new DBMSException("the database has not existed!");
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub

		String dName = ((String) args[0]);
		if (isGrammarLegal(dName)) {
			File db = new File(Path.dbPath(dName));
			if (db.isDirectory()) {
				for (File table : db.listFiles())
					table.delete();
				db.delete();
				System.out.println("delete database sucessfully!");
				return;
			}
		}

	}

	@Test
	public void test() {
		try {
			operate("ych");
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
