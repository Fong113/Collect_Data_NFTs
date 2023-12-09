package marketplace.crawl.niftygateway;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.ChainType;

public enum NiftygatewayChainType implements ChainType{
	
	ETH("ETH"), USD("USD");
	
	private String value;
	
	private NiftygatewayChainType(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public static List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(NiftygatewayChainType n : NiftygatewayChainType.values()) {
			values.add(n.value);
		}
		return values;
	}
}
