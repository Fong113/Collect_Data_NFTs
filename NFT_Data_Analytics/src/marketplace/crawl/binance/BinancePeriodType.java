package marketplace.crawl.binance;

import marketplace.crawl.PeriodType;

public enum BinancePeriodType implements PeriodType{
	ONEHOUR("1H"), SIXHOURS("6H"), ONEDAY("24H"), ONEWEEK("7D");
	
	private String value;
	
	private BinancePeriodType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
