package marketplace.crawl.opensea;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.PeriodType;

public enum OpenseaPeriodType implements PeriodType{
//	ONEHOUR("ONE_HOUR"), SIXHOURS("SIX_HOUR"), 
	ONEDAY("ONE_DAY"), ONEWEEK("SEVEN_DAY"); 
//	ONEMONTH("THIRTY_DAY");
	
	private String value;
	
	private OpenseaPeriodType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(OpenseaPeriodType o : OpenseaPeriodType.values()) {
			values.add(o.value);
		}
		return values;
	}
}
