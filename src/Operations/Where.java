package Operations;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.ConnTuple;
import Elements.QueryTable;
import Elements.Table;
import Elements.Tuple;
import Symbols.SymbolTree;

public class Where extends AbstractOperation implements Segment, Comparable {

	static private Pattern pattern = Pattern.compile("\s*where\s+(.*)\s*");

	public String data;
	public SymbolTree root;
	public String beforeData;
	public String processData;
	public int reSort = 1;

	public Where() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 4;
	}

	public Where(String data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}

	public boolean isWhereExisted() {
		Matcher matcher = pattern.matcher(data);
		if (matcher.find()) {
			this.data = matcher.group(1);
			return true;
		} else
			return false;
	}

	@Override
	public void operate(Object... args) throws DBMSException {
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
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Segment segment = (Segment) o;
		if (this.getPriority() < segment.getPriority())
			return -1;
		else if (this.getPriority() == segment.getPriority())
			return 0;
		else
			return 1;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		SymbolTree tree = new SymbolTree();
		try {
			if (this.processData == null)
				this.processData = this.data;
			root = tree.createTree(this.processData);
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
	public void getProcess(String data) {
		// TODO Auto-generated method stub
		this.beforeData = data;
		if (this.beforeData == null)
			this.beforeData = "";
		Pattern p = Pattern.compile("(.*)\s*" + this.beforeData);
		Matcher matcher = p.matcher(this.data);
		if (matcher.find()) {
			this.processData = matcher.group(1);
		}

	}

	@Override
	public String afterProcess() {
		// TODO Auto-generated method stub
		return "where.*";
	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		this.data = data;
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
		ArrayList<ConnTuple> bin = new ArrayList<ConnTuple>();
		for (ConnTuple tuple : table.queryTuples)
			try {
				if (!root.isTreeLegal(table, tuple))
					bin.add(tuple);
			} catch (DBMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		table.queryTuples.removeAll(bin);

	}

}
