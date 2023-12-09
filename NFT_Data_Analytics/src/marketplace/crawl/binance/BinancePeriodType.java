package marketplace.crawl.binance;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(BinancePeriodType b : BinancePeriodType.values()) {
			values.add(b.value);
		}
		return values;
	}
}
