package marketplace.crawl.binance;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.ChainType;

public enum BinanceChainType implements ChainType {
	
	BNB("BSC"), ETH("ETH"), BTC("BTC");
	
	private String value;
	
	private BinanceChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(BinanceChainType b : BinanceChainType.values()) {
			values.add(b.value);
		}
		return values;
	}
}
