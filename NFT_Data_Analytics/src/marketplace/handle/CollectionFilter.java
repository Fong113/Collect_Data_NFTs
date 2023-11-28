package marketplace.handle;

public class CollectionFilter extends Collection {
	private String marketplaceName;
	private String chain;
	private String period;
	
	public CollectionFilter(String id, String logo, String name, double volume,
			double volumeChange, double floorPrice, double floorPriceChange, int items, int owners) {
		
		super(id, logo, name, volume, volumeChange, floorPrice, floorPriceChange, items, owners);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return marketplaceName + " " + period + " " + chain + " " + " " + super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CollectionFilter) {
			CollectionFilter col = (CollectionFilter) obj;
			return this.marketplaceName == col.marketplaceName && this.chain == col.chain && this.period == col.period && this.getName() == col.getName();
		}
		return false;
	}
	
	public String getMarketPlaceName() {
		return marketplaceName;
	}

	public void setMarketPlaceName(String marketPlaceName) {
		this.marketplaceName = marketPlaceName;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
