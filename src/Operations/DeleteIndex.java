package Operations;

import java.io.File;

import Configs.DBMSException;
import Configs.Loader;
import Configs.Path;

public class DeleteIndex extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String iName = (String) args[0];
		if (Loader.isIndexExisted(iName)) {
			File index = new File(Path.indexPath(iName));
			index.delete();
			System.out.println("delete index sucessfully!");
		} else
			throw new DBMSException("the index has not existed!");
	}

}
