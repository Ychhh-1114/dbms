package Operations;

import java.util.ArrayList;

import Elements.QueryTable;
import Elements.Table;

public interface Segment {
	public int getPriority();

	public ArrayList<String> getTables();

	public void process();

	public void getProcess(String data);

	public String afterProcess();

	public void setData(String data);

	public int getResort();

	public void constrTable(QueryTable table);

}
