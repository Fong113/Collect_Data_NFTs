package marketplace.crawl.rarible;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.PeriodType;

public enum RariblePeriodType implements PeriodType{
	ONEHOUR("H1"), ONEDAY("DAY"), ONEWEEK("WEEK"), ONEMONTH("MONTH");
	
	private String value;
	
	private RariblePeriodType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(RariblePeriodType r : RariblePeriodType.values()) {
			values.add(r.value);
		}
		return values;
	}
}
