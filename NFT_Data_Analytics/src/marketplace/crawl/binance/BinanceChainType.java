package marketplace.crawl.binance;

import marketplace.crawl.ChainType;

public enum BinanceChainType implements ChainType{
	
	BNB("BSC"), ETH("ETH"), BTC("BTC");
	
	private String value;
	
	private BinanceChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
