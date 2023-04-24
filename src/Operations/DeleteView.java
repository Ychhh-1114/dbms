package Operations;

import java.io.File;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Configs.Path;

public class DeleteView extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String vName = (String) args[0];
		if (Loader.isViewExisted(vName)) {
			File view = new File(Path.viewPath(vName));
			view.delete();
			System.out.println("delete view sucessfully!");
		} else
			throw new DBMSException("the view has not existed!");

	}

	@Test
	public void test() {
		String sql[] = { "vv" };
		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
