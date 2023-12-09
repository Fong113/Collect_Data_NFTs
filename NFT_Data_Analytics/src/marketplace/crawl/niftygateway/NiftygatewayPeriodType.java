package marketplace.crawl.niftygateway;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(NiftygatewayPeriodType n : NiftygatewayPeriodType.values()) {
			values.add(n.value);
		}
		return values;
	}
}
