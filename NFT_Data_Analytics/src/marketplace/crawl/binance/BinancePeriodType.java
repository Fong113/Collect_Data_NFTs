package marketplace.crawl.binance;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.type.PeriodType;

public enum BinancePeriodType implements PeriodType{
//	SIXHOURS("6H"),
	ONEHOUR("1H"), ONEDAY("24H"), ONEWEEK("7D");
	
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
