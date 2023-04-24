package Operations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AggregateFunction.FuncManage;
import AggregateFunction.Function;
import Configs.DBMSException;
import Configs.Loader;
import Elements.Column;
import Elements.DataBase;
import Elements.QueryTable;
import Elements.Table;

public class Select extends AbstractOperation implements Segment, Serializable {

	static private Pattern detail = Pattern.compile("(.*)\\.(.*)");

	public int reSort;
	public String processData;
	public String data;
	public boolean isFunc = false;
	public boolean isCol = false;
	public ArrayList<Function> funcs;
	// load all tables in need

	public HashMap<Table, HashSet<String>> selectmap = new HashMap();
	public boolean isAll = false;

	// func + cols
	public Select(String data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public String afterProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getProcess(String data) {
		// TODO Auto-generated method stub

	}

	public void processSelect(ArrayList<String> tNames)
			throws DBMSException, InstantiationException, IllegalAccessException {
		for (String tName : tNames) {
			Table table = Loader.getTable(tName.trim());
			if (table == null) {
				DataBase.isRightRe = false;
				throw new DBMSException("has not this table");
			}
			if (!selectmap.containsKey(table))
				selectmap.put(table, new HashSet<String>());
		}

		String each[] = this.data.split(",");
		for (String str : each) {
			str = str.trim();
			Matcher matcher = detail.matcher(str);
			if (matcher.find()) {
				String tName = matcher.group(1);
				String cName = matcher.group(2);
				if (tNames.contains(tName)) {
					for (Table table : selectmap.keySet()) {
						if (table.tName.equals(tName)) {
							if (table.cols.contains(new Column(cName))) {
								selectmap.get(table).add(cName);
							} else {
								DataBase.isRightRe = false;
								throw new DBMSException("has not this column");
							}
						}
					}
				} else {
					DataBase.isRightRe = false;
					throw new DBMSException("has not the table");
				}

			} else {
				if (str.equals("*")) {
					if (!this.isCol && !this.isFunc) {
						isAll = true;
						return;
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the select parameter is worse!");
					}
				} else if (FuncManage.isQueryFunc(str)) {
					if (!this.isCol) {
						if (this.funcs == null)
							this.funcs = new ArrayList<Function>();
						this.isFunc = true;
						this.funcs.add(FuncManage.getFunc(str, selectmap.keySet()));
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the select segment has not aggregation function!");

					}
				} else {
					if (!this.isFunc) {
						this.isCol = true;

						boolean find = false;
						Table temp = null;
						for (Table table : selectmap.keySet()) {
							if (table.cols.contains(new Column(str))) {
								temp = table;
								find = !find;
							}
						}
						if (!find) {
							DataBase.isRightRe = false;
							throw new DBMSException("the column is illegal!");
						} else
							selectmap.get(temp).add(str);
					} else {
						DataBase.isRightRe = false;
						throw new DBMSException("the select segment has aggregation function!");
					}
				}
			}
		}

	}

	@Override
	public void process() {

	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<String> getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResort() {
		// TODO Auto-generated method stub
		return this.reSort;
	}

	@Override
	public void constrTable(QueryTable table) {
		// TODO Auto-generated method stub

	}

}
