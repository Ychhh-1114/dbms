package Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;
import Operations.QueryControl;
import Operations.Select;

public class View extends AbstractElements {
	private static final long serialVersionUID = 30L;

	public String vName;
	public String vData;
	public QueryTable vTable = null;
	public Select vSelect = null;

	static private Pattern pattern = Pattern.compile("\s*select\s+(.*)\s+from\s+(.*)\s*");

	public View(String name, String data) {
		// TODO Auto-generated constructor stub
		this.vName = name;
		this.vData = data;
	}

	public boolean parse() throws DBMSException {
		Matcher matcher = pattern.matcher(this.vData);
		if (matcher.find()) {
			String cNames = matcher.group(1);
			String params = matcher.group(2);
			QueryControl control = new QueryControl();
			QueryTable table = null;
			Select select = new Select(cNames);
			try {
				control.getQueryControl("from " + params);
				table = control.launch(select);
			} catch (InstantiationException | IllegalAccessException | CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.vTable = table;
			this.vSelect = select;
			return true;
		}
		return false;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}
