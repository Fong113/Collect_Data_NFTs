package marketplace.crawl.rarible;

import java.net.URI;
import java.net.http.HttpRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import marketplace.crawl.Crawler;
import marketplace.crawl.MarketplaceType;

public class Rarible extends Crawler {
	
	public Rarible(String chain, String period) {
		super.chain = chain;
		super.period = period;
		super.marketplaceName = MarketplaceType.RARIBLE.getValue();
	}
	
	
	@Override
	protected void getRespone() {
		String requestBody = "{\"size\":100 ,\"filter\":{\"verifiedOnly\":false,\"sort\":\"VOLUME_DESC\",\"blockchains\":[\""+ chain +"\"],\"showInRanking\":false,\"period\":\"" + period + "\",\"hasCommunityMarketplace\":false,\"currency\":\"NATIVE\"}}";
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://rarible.com/marketplace/api/v4/collections/search"))
			    .header("Content-Type", "application/json") 
			    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 OPR/103.0.0.0")
			    .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
			    .build();
		respone = Crawler.getResponeRequest(request);
	}
	
	@Override
	protected void preprocessData() {
		JsonArray rowsRaw = JsonParser.parseString(respone).getAsJsonArray();
		
		JsonArray rows = new JsonArray();
		String currency = "";
		for(JsonElement rowRaw : rowsRaw) {
			JsonObject rowRawObj = rowRaw.getAsJsonObject();
			JsonObject row = new JsonObject();
			row.add("id", rowRawObj.get("id"));
			row.add("name", rowRawObj.get("name"));
			
			String logo = "";
			JsonObject rowRawObjCollection = rowRawObj.getAsJsonObject("collection");
			if(isGet(rowRawObjCollection, "pic")) {
				String pic = rowRawObjCollection.get("pic").getAsString(); 
				logo =  pic.contains("ipfs") ? "" : pic; 
			}
			if(rowRawObjCollection.getAsJsonArray("imageMedia").size() >= 1) {
				logo = rowRawObjCollection.getAsJsonArray("imageMedia")
						.get(1)
						.getAsJsonObject()
						.get("url")
						.getAsString();
			}
			
			row.addProperty("logo", logo);
			JsonObject rowRawObjStatistics = rowRawObj.getAsJsonObject("statistics");
			row.add("floorPrice", isGet(rowRawObjStatistics, "floorPrice") ? rowRawObjStatistics.getAsJsonObject("floorPrice").get("value") : null);
			row.add("floorPriceChange", isGet(rowRawObjStatistics, "floorPrice") ? rowRawObjStatistics.getAsJsonObject("floorPrice").get("changePercent") : null);
			row.add("volume", isGet(rowRawObjStatistics, "amount") ? rowRawObjStatistics.getAsJsonObject("amount").get("value") : null);
			row.add("volumeChange", isGet(rowRawObjStatistics, "usdAmount") ? rowRawObjStatistics.getAsJsonObject("usdAmount").get("changePercent") : null);
			row.add("owners", isGet(rowRawObjStatistics, "owners") ? rowRawObjStatistics.get("owners") : null);
			row.add("items", isGet(rowRawObjStatistics, "items") ? rowRawObjStatistics.get("items") : null);
			rows.add(row);
			
			if(currency.equals("")) {
				currency = isGet(rowRawObjStatistics, "floorPrice") ? rowRawObjStatistics.getAsJsonObject("floorPrice").get("currency").getAsString() : "";
			}
		}
		
		data.addProperty("marketplaceName", "Rarible");
		data.add("createdAt", new JsonPrimitive(Crawler.getTimeCrawl("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.addProperty("currency", currency);
		data.add("data", rows);
	}
}