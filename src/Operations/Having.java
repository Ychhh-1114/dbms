package Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AggregateFunction.Function;
import Configs.DBMSException;
import Elements.QueryTable;
import Symbols.SymbolTree;

public class Having extends AbstractOperation implements Segment, Comparable {

	public String data;
	static private Pattern pattern = Pattern.compile(".*havinv\s+on\s+(.*)");
	public SymbolTree root;
	public int reSort;

	public boolean isHasHving(String sql) {
		Matcher matcher = pattern.matcher(sql);
		if (matcher.find()) {
			this.data = matcher.group(1);
			return true;
		} else
			return false;
	}

	@Override
	public void operate(Object... args) throws DBMSException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String afterProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		this.data = data;
	}

	@Override
	public void getProcess(String data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		SymbolTree tree = new SymbolTree();
		try {
			root = tree.createTree(this.data);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		HashMap<String, QueryTable> groups = table.groups;
		ArrayList<Function> funcs = new ArrayList();

		HashMap<String, QueryTable> afterHaving = new HashMap<String, QueryTable>();
		for (String val : table.groups.keySet()) {
			QueryTable t = table.groups.get(val);
			if (this.root.isValueLegal(t))
				afterHaving.put(val, t);
		}

		table.groups = afterHaving;

		return;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
