package marketplace.crawl;

public enum MarketplaceType {
	OPENSEA("opensea"), BINANCE("binance"), RARIBLE("rarible"), NIFTYGATEWAY("niftygateway");
	
	private String value;
	
	private MarketplaceType(String value) {
		this.value = value;
	}
}
