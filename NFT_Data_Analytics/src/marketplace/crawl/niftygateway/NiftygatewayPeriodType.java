package marketplace.crawl.niftygateway;

import marketplace.crawl.PeriodType;

public enum NiftygatewayPeriodType implements PeriodType{
	ONEDAY("oneDay"), ONEWEEK("sevenDay"), ONEMONTH("thirtyDay");
	
	private String value;
	
	private NiftygatewayPeriodType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
