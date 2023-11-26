package marketplace.crawl;

import java.net.URI;
import java.net.http.HttpRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

class NiftyGateway extends Crawler {
	
	public enum Chain {
		ETH("ETH"), USD("USD");
		
		private String value;
		
		private Chain(String value) {
			this.value = value;
		}
	}
	
	public enum Period {
		ONEDAY("oneDay"), ONEWEEK("sevenDay"), ONEMONTH("thirtyDay");
		
		private String value;
		
		private Period(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	
	public NiftyGateway(Period period, Chain chain, int rows) {
		super.period = period.value;
		super.chain = chain.value;
		super.rows = rows;
		
	}
	
	public NiftyGateway(Period period, Chain chain) {
		super.period = period.value;
		super.chain = chain.value;
		super.rows = 100;	
	}
	
	
	@Override
	protected void getRespone() {
		String api = "";
		switch(super.period) {
		case "oneDay":
			api = "https://api.niftygateway.com/stats/rankings/?page=1&page_size="+ rows +"&sort=-one_day_total_volume";
			break;
		case "sevenDay":
			api = "https://api.niftygateway.com/stats/rankings/?page=1&page_size="+ rows +"&sort=-seven_day_total_volume";
			break;
		case "thirtyDay":
			api = "https://api.niftygateway.com/stats/rankings/?page=1&page_size="+ rows +"&sort=-thirty_day_total_volume";
			break;
		}
		
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create(api))
			    .header("Content-Type", "application/json") 
			    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 OPR/103.0.0.0")
			    .method("GET", HttpRequest.BodyPublishers.noBody())
			    .build();
		respone = Crawler.getResponeRequest(request);
		
	}
	
	@Override
	protected void preprocessData() {
		
		float rate = chain.equals("USD") ? 100 : getFixRates() * 100;
		
		JsonArray rowsRaw = JsonParser.parseString(respone)
				.getAsJsonObject()
				.getAsJsonArray("results");

		JsonArray rows = new JsonArray();
		for(JsonElement rowRaw : rowsRaw) {
			JsonObject rowRawObj = rowRaw.getAsJsonObject();
			JsonObject row = new JsonObject();
			row.add("id", rowRawObj.getAsJsonObject("collection").get("niftyTitle"));
			row.add("name", rowRawObj.getAsJsonObject("collection").get("niftyTitle"));	
			row.add("logo", rowRawObj.getAsJsonObject("collection").get("niftyDisplayImage"));
			row.add("volume",  isGet(rowRawObj, period + "TotalVolume") ? convertValueByNetworkRate(rowRawObj.get(period + "TotalVolume"), rate) : null);
			row.add("volumeChange", isGet(rowRawObj, period + "Change") ? convertValueByNetworkRate(rowRawObj.get(period + "Change"), 1) : null);
			row.add("floorPrice", isGet(rowRawObj, "floorPrice") ? convertValueByNetworkRate(rowRawObj.get("floorPrice"), rate) : null);
//			row.add("avgPrice", isGet(rowRawObj, "avgSalePrice") ? convertValueByNetworkRate(rowRawObj.get("avgSalePrice"), rate) : null);
			row.add("floorPriceChange", null);
			row.add("items", rowRawObj.get("totalSupply"));
			row.add("owners", rowRawObj.get("numOwners"));
			rows.add(row);
		}
		
		data.add("createdAt", new JsonPrimitive(Crawler.getTime("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.add("data", rows);
	}

	@Override
	protected String getFileName() {
		String formatTime = Crawler.getTime("yyy_MM_dd_HH");
		return ".\\data\\niftygateway_" + period + "_" + chain + "_" + formatTime + ".json";
	}
	
	
	private float getFixRates() {		
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://api.niftygateway.com/v1/fxrates/?source_currency=ETH&base_currency=USD&order_by=-created_at&limit=1"))
			    .header("Content-Type", "application/json") 
			    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 OPR/103.0.0.0")
			    .method("GET", HttpRequest.BodyPublishers.noBody())
			    .build();
		
		String res = Crawler.getResponeRequest(request);
		
		return JsonParser.parseString(res)
				.getAsJsonObject()
				.getAsJsonArray("results")
				.get(0)
				.getAsJsonObject()
				.get("price")
				.getAsFloat();
		
	}
	
	private JsonElement convertValueByNetworkRate(JsonElement jElement, float rate) {
		return new JsonPrimitive(jElement.getAsFloat() / rate);
	}
}