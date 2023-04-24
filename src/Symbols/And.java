package Symbols;

import java.util.HashMap;
import java.util.regex.Pattern;

public class And extends SymbolNode implements Comparable {

	/////////////////////////////////////////
	public And(String data) {
		super(data);
	}

	public And() {
		// TODO Auto-generated constructor stub
		this.symbol = "and";
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub

		return 0;
	}

}
