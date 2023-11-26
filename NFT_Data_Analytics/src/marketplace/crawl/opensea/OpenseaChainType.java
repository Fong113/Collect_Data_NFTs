package marketplace.crawl.opensea;

import marketplace.crawl.ChainType;

public enum OpenseaChainType implements ChainType{
	
	ALLCHAIN(""), ARBITRUM("arbitrum"), AVALANCHE("avalanche"), BNB("BSC"), 
	BASE("base"), ETH("ethereum"), KLAYTN("klaytn"), OPTIMISM("optimism"), 
	POLYGON("matic"), SOLANA("solana"), ZORA("zora");
	
	private String value;
	
	private OpenseaChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
}
