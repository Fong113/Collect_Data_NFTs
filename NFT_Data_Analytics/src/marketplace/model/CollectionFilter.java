package marketplace.model;

public class CollectionFilter extends Collection {
	private String marketplaceName;
	private String currency;
	private String chain;
	private String period;

	public CollectionFilter(String id, String logo, String name, double volume, double volumeChange, double floorPrice,
			double floorPriceChange, int items, int owners, String marketplaceName, String currency, String chain,
			String period) {
		super(id, logo, name, volume, volumeChange, floorPrice, floorPriceChange, items, owners);
		this.marketplaceName = marketplaceName;
		this.currency = currency;
		this.chain = chain;
		this.period = period;
	}
	
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return marketplaceName + " " + period + " " + currency + " " + " " + super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CollectionFilter) {
			CollectionFilter col = (CollectionFilter) obj;
			return this.marketplaceName == col.marketplaceName 
					&& this.currency == col.currency 
					&& this.period == col.period 
					&& this.getName() == col.getName();
		}
		return false;
	}
	
	public String getMarketplaceName() {
		return marketplaceName;
	}

	public void setMarketplaceName(String marketplaceName) {
		this.marketplaceName = marketplaceName;
	}


	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}
}
