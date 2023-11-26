package marketplace.crawl.opensea;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import marketplace.crawl.Crawler;

public class Opensea extends Crawler {
	
	public Opensea(String chain, String period, int rows) {
		super.chain = chain;
		super.period = period;
		super.rows = rows;
	}
	
	public Opensea(String chain, String period) {
		super.chain = chain;
		super.period = period;
		super.rows = 100;
	}
	
	@Override
	protected void getRespone() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\ChromeDriver\\chromedriver.exe");
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless");
		WebDriver driver = new ChromeDriver();
        
		String url = "https://opensea.io/rankings/trending?chain="+ chain +"&sortBy="+ period.toLowerCase() + "_volume";
        driver.navigate().to(url);
        try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        respone =  driver.findElement(By.cssSelector("script[id=__NEXT_DATA__]"))
        		.getAttribute("innerHTML");
        
        driver.quit();
	}
	
	@Override
	protected void preprocessData() {
		JsonObject recordsRaw = JsonParser.parseString(respone)
				.getAsJsonObject()
        		.getAsJsonObject("props")
        		.getAsJsonObject("pageProps")
        		.getAsJsonObject("initialRecords");

		JsonArray rows = new JsonArray();
		JsonObject curRow = null;
		
        Set<Entry<String, JsonElement>> entrySet = recordsRaw.entrySet();
        
        int index = -2;

        for(Map.Entry<String,JsonElement> entry : entrySet){
        	if(index <= 0) {
        		index++;
        		continue;
        	}
        	
        	String key = entry.getKey();
        	
        	if(key.startsWith("client:root:__RankingsPageTop_rankings_connection"))
        		break;
        	
        	if(key.startsWith("client:root:topCollectionsByCategory"))
        		continue;
        	
        	JsonObject e = entry.getValue().getAsJsonObject();

        	if(!key.startsWith("client")) {
        		curRow = new JsonObject();
        		curRow.add("id", e.get("__id"));
        		curRow.add("name", e.get("name"));
        		curRow.add("logo", e.get("logo"));
        		curRow.add("floorPriceChange", e.get("floorPricePercentChange(statsTimeWindow:\"" + period + "\")"));
        	}
        	
        	
        	if(key.endsWith("\")")) {
        		curRow.add("owners", e.get("numOwners"));
        		curRow.add("items", e.get("totalSupply"));
        		curRow.add("volumeChange", e.get("volumeChange"));
//        		curRow.add("numOfSales", e.get("numOfSales"));
        	}
        	
        	if(key.endsWith("floorPrice")) {
        		curRow.add("floorPrice", e.get("unit"));
        	}
        	
        	if(key.endsWith("volume")) {
        		curRow.add("volume", e.get("unit"));
        	}
        	
        	if(key.endsWith(":edges:" + index) || index == 100) {
        		
        		if(!curRow.has("floorPrice")) {
        			curRow.add("floorPrice", null);
        		}
        		rows.add(curRow);
        		index++;
        	}
        	
        }
        
        data.add("createdAt", new JsonPrimitive(Crawler.getTime("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.add("data", rows);
	}

	@Override
	public String getFileName() {
		return ".\\data\\opensea_" + period + "_" + chain + ".json";
	}
}
