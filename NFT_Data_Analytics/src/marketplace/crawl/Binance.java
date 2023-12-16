package marketplace.crawl;

import java.net.URI;
import java.net.http.HttpRequest;

import org.openqa.selenium.TimeoutException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import marketplace.crawl.exception.InternetConnectionException;

public class Binance extends Crawler {

	public Binance() {
		super.marketplaceName = "binance";
	}

	public Binance(String chain, String period) {
		super.chain = chain;
		super.period = period;
		super.marketplaceName = "binance";
	}

	@Override
	protected void getData() throws TimeoutException, InternetConnectionException, Exception {
		String requestBody = "{\"network\":\"" + chain + "\",\"period\":\""
				+ MarketplaceType.BINANCE.mapPeriodToPeriodParam.get(period)
				+ "\",\"sortType\":\"volumeDesc\",\"page\":1,\"rows\":100}";
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://www.binance.com/bapi/nft/v1/friendly/nft/ranking/trend-collection"))
				.header("Content-Type", "application/json")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 OPR/103.0.0.0")
				.timeout(timeOut).method("POST", HttpRequest.BodyPublishers.ofString(requestBody)).build();

		respone = sendRequest(request);

	}

	@Override
	protected void preprocessData() {
		JsonArray rowsRaw = JsonParser.parseString(respone).getAsJsonObject().getAsJsonObject("data")
				.getAsJsonArray("rows");

		JsonArray rows = new JsonArray();

		for (JsonElement rowRaw : rowsRaw) {
			JsonObject rowRawObj = rowRaw.getAsJsonObject();
			JsonObject row = new JsonObject();
			row.add("id", rowRawObj.get("collectionId"));
			row.add("logo", rowRawObj.get("coverUrl"));
			row.add("name", rowRawObj.get("title"));
			row.add("volume", rowRawObj.get("volume"));
			row.add("volumeChange", rowRawObj.get("volumeRate"));
			row.add("floorPrice", rowRawObj.get("floorPrice"));
			row.add("floorPriceChange", rowRawObj.get("floorPriceRate"));
			row.add("items", rowRawObj.get("itemsCount"));
			row.add("owners", rowRawObj.get("ownersCount"));
			rows.add(row);
		}

		data.addProperty("marketplaceName", marketplaceName);
		data.add("createdAt", new JsonPrimitive(Crawler.getTimeCrawl("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.add("currency", new JsonPrimitive(chain));
		data.add("data", rows);
	}
}
