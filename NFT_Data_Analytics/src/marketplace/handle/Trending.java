package marketplace.handle;

import java.util.ArrayList;

import marketplace.crawl.MarketplaceType;

public class Trending {
	private MarketplaceType marketPlace;
	private String createdAt;
	private String chain;
	private String period;
	private String currency;
	private ArrayList<Collection> data;

	public Trending(MarketplaceType marketPlace, String createdAt, String chain, String period, String currency,
			ArrayList<Collection> data) {
		super();
		this.marketPlace = marketPlace;
		this.createdAt = createdAt;
		this.chain = chain;
		this.period = period;
		this.currency = currency;
		this.data = data;
	}

	public MarketplaceType getMarketPlace() {
		return marketPlace;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getChain() {
		return chain;
	}

	public String getPeriod() {
		return period;
	}

	public ArrayList<Collection> getData() {
		return data;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String unit) {
		this.currency = unit;
	}
}
