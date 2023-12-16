package marketplace.crawl;

import java.net.URI;
import java.net.http.HttpRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;

public class Niftygateway extends Crawler {

	public Niftygateway() {
		super.marketplaceName = "niftygateway";
	}

	public Niftygateway(String chain, String period) {
		super.period = period;
		super.chain = chain;
		super.marketplaceName = "niftygateway";
	}

	@Override
	protected void getData() throws CrawlTimeoutException, InternetConnectionException, Exception {
		String api = "https://api.niftygateway.com/stats/rankings/?page=1&page_size=100&sort=-"
				+ MarketplaceType.NIFTYGATEWAY.mapPeriodToPeriodParam.get(period);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(api)).header("Content-Type", "application/json")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 OPR/103.0.0.0")
				.timeout(timeOut).method("GET", HttpRequest.BodyPublishers.noBody()).build();

		respone = sendRequest(request);

	}

	@Override
	protected void preprocessData() {

		double rate = chain.equals("USD") ? 100 : 0.5 * 100;

		JsonArray rowsRaw = JsonParser.parseString(respone).getAsJsonObject().getAsJsonArray("results");

		JsonArray rows = new JsonArray();
		for (JsonElement rowRaw : rowsRaw) {
			JsonObject rowRawObj = rowRaw.getAsJsonObject();
			JsonObject row = new JsonObject();
			row.add("id", rowRawObj.getAsJsonObject("collection").get("niftyTitle"));
			row.add("name", rowRawObj.getAsJsonObject("collection").get("niftyTitle"));
			row.add("logo", rowRawObj.getAsJsonObject("collection").get("niftyDisplayImage"));
			row.add("volume",
					isGet(rowRawObj, period + "TotalVolume")
							? convertValueByNetworkRate(rowRawObj.get(period + "TotalVolume"), rate)
							: null);
			row.add("volumeChange",
					isGet(rowRawObj, period + "Change") ? convertValueByNetworkRate(rowRawObj.get(period + "Change"), 1)
							: null);
			row.add("floorPrice",
					isGet(rowRawObj, "floorPrice") ? convertValueByNetworkRate(rowRawObj.get("floorPrice"), rate)
							: null);
			row.add("floorPriceChange", null);
			row.add("items", rowRawObj.get("totalSupply"));
			row.add("owners", rowRawObj.get("numOwners"));
			rows.add(row);
		}

		data.addProperty("marketplaceName", marketplaceName);
		data.add("createdAt", new JsonPrimitive(Crawler.getTimeCrawl("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.add("currency", new JsonPrimitive(chain));
		data.add("data", rows);
	}

	private JsonElement convertValueByNetworkRate(JsonElement jElement, double rate) {
		return new JsonPrimitive(jElement.getAsFloat() / rate);
	}
}