package Keys;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Configs.DBMSException;

public class KeyManage {

	static private Pattern primary = Pattern.compile(".*primary\s+key.*");
	static private Pattern checked = Pattern.compile(".*checked\s*\\((.*)\\)\s*");
	static private Pattern unique = Pattern.compile(".*unique.*");
	static private Pattern notnull = Pattern.compile(".*not\s+null.*");

	static private HashMap<Pattern, Class> keymap = new HashMap();
	static {
		keymap.put(primary, PrimaryKey.class);
		keymap.put(checked, CheckedKey.class);
		keymap.put(unique, UniqueKey.class);
		keymap.put(notnull, NotnullKey.class);

	}

	static public Key getKey(String key) throws InstantiationException, IllegalAccessException, DBMSException {
		for (Pattern pattern : keymap.keySet()) {
			Matcher matcher = pattern.matcher(key);
			if (matcher.find()) {
				if (keymap.get(pattern) != CheckedKey.class)
					return (Key) keymap.get(pattern).newInstance();
				else {
					CheckedKey checkedKey = new CheckedKey();
					checkedKey.setData(matcher.group(1));
					return checkedKey;
				}
			}
		}
		return new DefaultKey();
	}

}
