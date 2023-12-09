package marketplace.crawl.rarible;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.ChainType;

public enum RaribleChainType implements ChainType{
	
//	IMMUTABLEX("IMMUTABLEX");
	ETH("ETHEREUM"), POLYGON("POLYGON"), TEZOS("TEZOS"); 
	
	private String value;
	
	private RaribleChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(RaribleChainType r : RaribleChainType.values()) {
			values.add(r.value);
		}
		return values;
	}
}
