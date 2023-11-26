package marketplace.crawl.rarible;

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
}
