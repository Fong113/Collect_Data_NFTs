package marketplace.crawl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum MarketplaceType {
	
	OPENSEA(
			Arrays.asList(
//					"arbitrum", 
//					"avalanche", 
//					"base", 
//					"klaytn", 
//					"optimism", 
//					"matic", 
//					"solana",
//					"zora", 
					"BSC", 
					"ethereum"
					),
			Map.of(
					"One Day", "one_day", 
					"One Week", "seven_day", 
					"One Month", "thirty_day"
					)),

	
	BINANCE(
			Arrays.asList(
//					"BSC", 
					"ETH", 
					"BTC"
					),
			Map.of(
					"One Hour", "1H", 
					"One Week", "SEVEN_DAY", 
					"One Month", "THIRTY_DAY"
					)),

	
	RARIBLE(
			Arrays.asList(
//					"IMMUTABLEX", 
//					"TEZOS", 
					"ETHEREUM", 
					"POLYGON"
					),
			Map.of(
					"One Hour", "H1", 
					"One Day", "DAY", 
					"One Week", "WEEK", 
					"One Month", "MONTH"
					)),

	NIFTYGATEWAY(
			Arrays.asList(
					"ETH", 
					"USD"
					),
			Map.of(
					"One Day", "one_day_total_volume", 
					"One Week", "seven_day_total_volume", 
					"One Month", "thirty_day_total_volume"
					));

	List<String> setChains;
	Map<String, String> mapPeriodToPeriodParam;

	MarketplaceType(List<String> setChains, Map<String, String> mapPeriodToPeriodParam) {
		this.setChains = setChains;
		this.mapPeriodToPeriodParam = mapPeriodToPeriodParam;
	}

	public List<String> getListChains() {
		return setChains;
	}

	public List<String> getListPeriods() {
		return new ArrayList<String>(mapPeriodToPeriodParam.keySet());
	}
	
	public static List<String> getListMarktplaceName() {
		List<String> result = new ArrayList<String>();
		for(MarketplaceType m : MarketplaceType.values()) {
			result.add(m.name());
		}
		return result;
	}
}
