package Operations;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Elements.QueryTable;
import Elements.Table;

public class From extends AbstractOperation implements Segment, Comparable {

	public ArrayList<String> tNames = new ArrayList<String>();
	public ArrayList<Table> tables;

	public String beforeData;
	public String data;
	public String processData;
	public int reSort = 4;

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
		return 5;
	}

	@Override
	public void getProcess(String data) {
		// TODO Auto-generated method stub
		this.beforeData = data;
		if (this.beforeData == null)
			this.beforeData = "";
		Pattern pattern = Pattern.compile("(.*)\s*" + this.beforeData);
		Matcher matcher = pattern.matcher(this.data);
		if (matcher.find()) {
			this.processData = matcher.group(1);
		}

	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		String tnames[] = this.processData.split(",");
		for (String str : tnames) {
			if (!tNames.contains(str))
				tNames.add(str);
		}

	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		this.data = data;
	}

	@Override
	public ArrayList<String> getTables() {
		// TODO Auto-generated method stub
		return this.tNames;
	}

	@Override
	public int getResort() {
		// TODO Auto-generated method stub
		return this.reSort;
	}

	@Override
	public void constrTable(QueryTable table) {
		// TODO Auto-generated method stub
		return;
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
}
