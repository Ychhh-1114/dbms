package Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Configs.Loader;
import Configs.Path;

public class TableManage {

	static public boolean isTableHasCol(Table table, Object... args) {
		String params[] = (String[]) args;
		for (String str : params)
			if (table.cols.contains(str))
				continue;
			else
				return false;

		return true;

	}

	static public boolean isTableHasCol(String tName, Object... args) {
		Table table = Loader.getTable(tName);
		if (table != null) {
			String[] params = (String[]) args;
			for (String str : params)
				if (table.cols.contains(str))
					continue;
				else
					return false;

			return true;
		}
		return false;

	}

	static public boolean isPrimaryOfTable(String tName, Object... args) {
		Table table = Loader.getTable(tName);
		if (table != null) {
			if (isTableHasCol(table, args)) {
				String[] params = (String[]) args;
				for (String str : params)
					if (table.primaryKey.cols.contains(str))
						continue;
					else
						return false;
				return true;

			}

		}
		return false;
	}

	static public boolean isPrimaryOfTable(Table table, Object... args) {
		if (isTableHasCol(table, args)) {
			String[] params = (String[]) args;
			for (String str : params)
				if (table.primaryKey.cols.contains(str))
					continue;
				else
					return false;
			return true;
		}
		return false;
	}

	static public void saveTable(Table table) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(Path.tablePath(table.tName));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(table);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static public void SaveView(View view) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(Path.viewPath(view.vName));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(view);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static public void saveIndex(Index index) {

	}

}
