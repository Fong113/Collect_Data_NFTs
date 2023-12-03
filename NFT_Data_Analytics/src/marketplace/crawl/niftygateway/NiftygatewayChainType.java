package marketplace.crawl.niftygateway;

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
}
