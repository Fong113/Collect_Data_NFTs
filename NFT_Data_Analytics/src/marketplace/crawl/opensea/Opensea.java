package marketplace.crawl.opensea;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import marketplace.crawl.Crawler;
import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.crawl.type.MarketplaceType;

public class Opensea extends Crawler {
	
	public Opensea(String chain, String period) {
		super.chain = chain;
		super.period = period;
		super.marketplaceName = MarketplaceType.OPENSEA.getValue();
	}
	
	@Override
	protected void getData() throws CrawlTimeoutException, InternetConnectionException, Exception{
		System.setProperty("webdriver.chrome.driver", ".\\lib\\ChromeDriver\\chromedriver.exe");
		ChromeOptions opt = new ChromeOptions();
		opt.setPageLoadStrategy(PageLoadStrategy.NONE);
		opt.addArguments("--headless");
		opt.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
		WebDriver driver = new ChromeDriver(opt);
		driver.manage().timeouts().pageLoadTimeout(timeOut);
		driver.manage().timeouts().scriptTimeout(timeOut);
		
		try {
			String url = "https://opensea.io/rankings/trending?chain="+ chain +"&sortBy="+ period.toLowerCase() + "_volume";
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.attributeContains(By.cssSelector("script[id=__NEXT_DATA__]"), "id", "__NEXT_DATA__"));
			respone =  driver.findElement(By.cssSelector("script[id=__NEXT_DATA__]"))
					.getAttribute("innerHTML");
//        driver.manage().deleteAllCookies();
			
		} catch (TimeoutException e) {
			throw new CrawlTimeoutException("Time out");
		} catch (WebDriverException e) {
//			e.printStackTrace();
			throw new InternetConnectionException("Check your connection");
		} catch (Exception e) {
			throw new Exception("Something went wrong");
		}
		finally {			
			driver.quit();
		}
		
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
        String currency = "";
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
        	}
        	
        	if(key.endsWith("floorPrice")) {
        		curRow.add("floorPrice", e.get("unit"));
        		
        		if(currency.equals("")) {
        			currency = e.get("symbol").getAsString();
        		}
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
        
		data.addProperty("marketplaceName", "Opensea");
        data.add("createdAt", new JsonPrimitive(Crawler.getTimeCrawl("MM/dd/yyy HH:MM:SS")));
		data.add("chain", new JsonPrimitive(chain));
		data.add("period", new JsonPrimitive(period));
		data.addProperty("currency", currency);
		data.add("data", rows);
	}
}
