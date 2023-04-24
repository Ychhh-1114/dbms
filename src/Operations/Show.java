package Operations;

import java.io.File;

import Configs.DBMSException;
import Configs.Path;
import Elements.DataBase;

public class Show extends AbstractOperation {

	public void showDataBase() {
		File database = new File(Path.dataBase.toString());
		for (File db : database.listFiles()) {
			System.out.println("database: " + db.getName());
			for (File table : db.listFiles())
				System.out.println("table: " + table.getName());
		}

		this.showViews();

		this.showIndexes();
	}

	public void showViews() {
		File views = new File(Path.view.toString());
		for (File view : views.listFiles())
			System.out.println("view: " + view.getName());
	}

	public void showIndexes() {
		File indexes = new File(Path.index.toString());
		for (File index : indexes.listFiles())
			System.out.println("index: " + index.getName());
	}

	public void showTable() {
		File database = new File(Path.dbPath(DataBase.currentDataBase));
		System.out.println("database: " + database.getName());
		for (File table : database.listFiles()) {
			System.out.println("table: " + table.getName());

		}
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String type = (String) args[0];
		switch (type) {
		case "database":
			this.showDataBase();
			break;
		case "table":
			this.showTable();
			break;
		case "index":
			this.showIndexes();
			break;
		case "views":
			this.showViews();
			break;
		default:
			throw new DBMSException("can't match the segment");
		}
	}

}
