package marketplace.crawl;

import java.net.URI;
import java.net.http.HttpRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

class Rarible extends Crawler {
	
	public enum Chain {
		ETH("ETHEREUM"), POLYGON("POLYGON"), TEZOS("TEZOS"), IMMUTABLEX("IMMUTABLEX");
		
		private String value;
		
		private Chain(String value) {
			this.value = value;
		}
	}
	
	public enum Period {
		ONEHOUR("H1"), ONEDAY("DAY"), ONEWEEK("WEEK"), ONEMONTH("MONTH");
		
		private String value;
		
		private Period(String value) {
			this.value = value;
		}
	}
	
	
	public Rarible(Chain chain, Period period, int rows) {
		super.chain = chain.value;
		super.period = period.value;
		super.rows = rows;
	}
	
	public Rarible(Chain chain, Period period) {
		super.chain = chain.value;
		super.period = period.value;
		super.rows = 100;
	}
	
	
	@Override
	protected void getRespone() {
		String requestBody = "{\"size\":"+ rows +",\"filter\":{\"verifiedOnly\":false,\"sort\":\"VOLUME_DESC\",\"blockchains\":[\""+ chain +"\"],\"showInRanking\":false,\"period\":\"" + period + "\",\"hasCommunityMarketplace\":false,\"currency\":\"NATIVE\"}}";
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
		System.out.println(respone);
		JsonArray rowsRaw = JsonParser.parseString(respone).getAsJsonArray();
		
		JsonArray rows = new JsonArray();
		for(JsonElement rowRaw : rowsRaw) {
			JsonObject rowRawObj = rowRaw.getAsJsonObject();
			JsonObject row = new JsonObject();
			row.add("id", rowRawObj.get("id"));
			row.add("name", rowRawObj.get("name"));
			row.add("logo", rowRawObj.getAsJsonObject("collection").get("pic"));
			
			JsonObject rowRawObjStatistics = rowRawObj.getAsJsonObject("statistics");
			row.add("floorPrice", isGet(rowRawObjStatistics, "floorPrice") ? rowRawObjStatistics.getAsJsonObject("floorPrice").get("value") : null);
			row.add("floorPriceChange", isGet(rowRawObjStatistics, "floorPrice") ? rowRawObjStatistics.getAsJsonObject("floorPrice").get("changePercent") : null);
			row.add("volume", isGet(rowRawObjStatistics, "amount") ? rowRawObjStatistics.getAsJsonObject("amount").get("value") : null);
			row.add("volumeChange", isGet(rowRawObjStatistics, "usdAmount") ? rowRawObjStatistics.getAsJsonObject("usdAmount").get("changePercent") : null);
			row.add("owners", isGet(rowRawObjStatistics, "owners") ? rowRawObjStatistics.get("owners") : null);
			row.add("items", isGet(rowRawObjStatistics, "items") ? rowRawObjStatistics.get("items") : null);
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
		return ".\\data\\rarible_" + period + "_" + chain + "_" + formatTime + ".json";
	}
}