package marketplace.model;

import java.util.ArrayList;

public class Trending {
	private String marketplaceName;
	private String createdAt;
	private String chain;
	private String period;
	private String currency;
	private ArrayList<Collection> data;

	public Trending(String marketplaceName, String createdAt, String chain, String period, String currency,
			ArrayList<Collection> data) {
		super();
		this.marketplaceName = marketplaceName;
		this.createdAt = createdAt;
		this.chain = chain;
		this.period = period;
		this.currency = currency;
		this.data = data;
	}

	public String getMarketplaceName() {
		return marketplaceName;
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
