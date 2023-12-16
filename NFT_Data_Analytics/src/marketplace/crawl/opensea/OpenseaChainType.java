package marketplace.crawl.opensea;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.type.ChainType;

public enum OpenseaChainType implements ChainType{
	
//	ARBITRUM("arbitrum"), AVALANCHE("avalanche"),  
//	BASE("base"), ETH("ethereum"), KLAYTN("klaytn"), OPTIMISM("optimism"), 
//	POLYGON("matic"), SOLANA("solana"), ZORA("zora");
	BNB("BSC"), ETH("ethereum"); 
//	POLYGON("matic");
	
	private String value;
	
	private OpenseaChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(OpenseaChainType o : OpenseaChainType.values()) {
			values.add(o.value);
		}
		return values;
	}
}
