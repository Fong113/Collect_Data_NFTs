package marketplace.crawl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonObject;

import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;

public abstract class Crawler  {
	protected String marketplaceName;
	protected String period;
	protected String chain;
	protected String respone;
	protected JsonObject data = new JsonObject();
	protected Duration timeOut = Duration.ofSeconds(15);
	protected static final String PATHSAVEDATA = ".\\data\\marketplace";
	
	
	protected abstract void getData() throws CrawlTimeoutException, InternetConnectionException, Exception;
	
	protected abstract void preprocessData();
	
	protected void saveDataToFile(File file) throws Exception{
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(data.toString());
			writer.close();
		} catch (IOException e) {
		    throw new Exception("Something went wrong", e);
		}
	}
	
	public void crawlTrendingAndSaveToFile() throws CrawlTimeoutException, InternetConnectionException, Exception {
		File file = new File(getFileSaveData(marketplaceName, period, chain));
		getData();
		preprocessData();			
		saveDataToFile(file);	
	}
	
	protected static String sendRequest(HttpRequest request) throws CrawlTimeoutException, InternetConnectionException, Exception {
		try {
			HttpResponse<String> res = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			 return res.body();
		} catch (HttpConnectTimeoutException e) {			
			throw new CrawlTimeoutException("Time out", e);
		} catch (IOException e) {
				throw new InternetConnectionException("Check your internet connection", e);				
		} catch (Exception e) {
			throw new Exception("Something went wrong", e);
		}
	}
	
	protected static String getFileSaveData(String marketplaceName, String period, String chain) {
		return PATHSAVEDATA + "\\" + marketplaceName + "_" + period + "_" + chain + ".json";
	}
	
	protected static boolean isGet(JsonObject jObject ,String property) {
		if(!jObject.has(property) || jObject.get(property).isJsonNull()) {			
			return false;
		}
		return true;
	}
	
	protected static String getTimeCrawl(String format) {
		LocalDateTime time = LocalDateTime.now();
		return time.format(DateTimeFormatter.ofPattern(format));
	}

	
	
	public void setPeriod(String period) {
		this.period = period;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

}
