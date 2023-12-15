package marketplace.crawl.type;

public enum MarketplaceType {
	OPENSEA("opensea"), BINANCE("binace"), RARIBLE("rarible"), NIFTYGATEWAY("niftygateway");
	
	private String value;
	
	private MarketplaceType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
