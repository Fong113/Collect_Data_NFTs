package marketplace.crawl.niftygateway;

import java.net.URI;
import java.net.http.HttpRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import marketplace.crawl.Crawler;

public class Niftygateway extends Crawler {
	
	public Niftygateway(String chain, String period, int rows) {
		super.period = period;
		super.chain = chain;
		super.rows = rows;
		
	}
	
	public Niftygateway(String chain, String period) {
		super.period = period;
		super.chain = chain;
		super.rows = 100;	
	}
	
	
	@Override
	protected void getRespone() {
		String api = "";
		switch(period) {
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
		
		data.addProperty("marketplaceName", "Niftygateway");
		data.add("createdAt", new JsonPrimitive(Crawler.getTime("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.add("currency", new JsonPrimitive(chain));
		data.add("data", rows);
	}

	@Override
	public String getFileName() {
		return PATHSAVEFILE + "\\niftygateway_" + period + "_" + chain + ".json";
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
	
	public static void crawlAllChainPeriod() {
		for(NiftygatewayChainType chain : NiftygatewayChainType.values()) {
			for(NiftygatewayPeriodType period: NiftygatewayPeriodType.values()) {
				Niftygateway niftygateway = new Niftygateway(chain.getValue(), period.getValue());
				niftygateway.crawlData();
				System.out.println("Done " + niftygateway.getFileName());
			}
		}
		System.out.println("Done Niftygateway");
	}
}