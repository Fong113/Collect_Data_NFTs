package marketplace.crawl.opensea;

import marketplace.crawl.PeriodType;

public enum OpenseaPeriodType implements PeriodType{
	ONEHOUR("ONE_HOUR"), SIXHOURS("SIX_HOUR"), ONEDAY("ONE_DAY"), ONEWEEK("SEVEN_DAY"), ONEMONTH("THIRTY_DAY");
	
	private String value;
	
	private OpenseaPeriodType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
