package Operations;

import Configs.DBMSException;
import Configs.Loader;
import Elements.Column;
import Elements.Index;
import Elements.Table;
import Elements.TableManage;
import Elements.Tuple;

public class CreateIndex extends AbstractOperation {

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub
		String iName = (String) args[0];
		String tName = (String) args[1];
		String params = (String) args[2];
		String cNames[] = params.split(",");
		if (!Loader.isIndexExisted(tName.trim())) {
			Table table = Loader.getTable(tName.trim());
			for (String c : cNames)
				if (!table.cols.contains(new Column(c.trim())))
					throw new DBMSException("table has not the column!");
			Index index = new Index(tName, iName, cNames);
			for (Tuple tuple : table.tuples) {
				String value = tuple.getValue(cNames);
				index.map.put(value, tuple);
			}
			TableManage.saveIndex(index);
			System.out.println("create index sucessfully!");
		} else
			throw new DBMSException("the index has existed!");

	}

}
