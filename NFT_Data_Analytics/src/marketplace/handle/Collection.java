package marketplace.handle;

public class Collection {
	private String id;
	private String logo;
	private String name;
	private float volume;
	private float volumeChange;
	private float floorPrice;
	private float floorPriceChange;
	private int items;
	private int owners;
	
	public Collection(String id, String logo, String name, float volume, float volumeChange, float floorPrice,
			float floorPriceChange, int items, int owners) {
		this.id = id;
		this.logo = logo;
		this.name = name;
		this.volume = volume;
		this.volumeChange = volumeChange;
		this.floorPrice = floorPrice;
		this.floorPriceChange = floorPriceChange;
		this.items = items;
		this.owners = owners;
	}
	
	@Override
	public String toString() {
		return id + " " + logo + " " + name + " " + " " + owners;
	}

	public String getId() {
		return id;
	}

	public String getLogo() {
		return logo;
	}

	public String getName() {
		return name;
	}

	public float getVolume() {
		return volume;
	}

	public float getVolumeChange() {
		return volumeChange;
	}

	public float getFloorPrice() {
		return floorPrice;
	}

	public float getFloorPriceChange() {
		return floorPriceChange;
	}

	public int getItems() {
		return items;
	}

	public int getOwners() {
		return owners;
	}
}
