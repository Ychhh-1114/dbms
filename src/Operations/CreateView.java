package Operations;

import javax.xml.crypto.Data;

import org.junit.jupiter.api.Test;

import Configs.DBMSException;
import Configs.Loader;
import Elements.DataBase;
import Elements.TableManage;
import Elements.View;

public class CreateView extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String vName = (String) args[0];
		String vData = (String) args[1];
		if (!Loader.isViewExisted(vName)) {
			View view = new View(vName, vData);
			view.parse();
			TableManage.SaveView(view);
			System.out.println("create view sucessfully!");

		} else
			throw new DBMSException("the view has existed!");
	}

	@Test
	public void test() {
		String sql[] = { "vv", "select name from ychhh" };
		try {
			DataBase.currentUser = Loader.getUser("root", "123");
		} catch (DBMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			operate(sql);
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
