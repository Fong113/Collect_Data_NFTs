package marketplace.crawl.rarible;

import marketplace.crawl.ChainType;

public enum RaribleChainType implements ChainType{
	
	ETH("ETHEREUM"), POLYGON("POLYGON"), TEZOS("TEZOS"), IMMUTABLEX("IMMUTABLEX");
	
	private String value;
	
	private RaribleChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
