package marketplace.crawl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.gson.JsonObject;

public abstract class Crawler {
	protected String marketplaceName;
	protected String period;
	protected String chain;
	protected String respone;
	protected JsonObject data = new JsonObject();
	protected static final String PATHSAVEFILE = ".\\data\\marketplace";
	
	protected abstract void getRespone();
	
	protected abstract void preprocessData();
	
	protected void saveDataToFile(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(data.toString());
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public JsonObject crawlTrendingAndSaveToFile() {
		File file = new File(getFileName(marketplaceName, period, chain));
		getRespone();
		preprocessData();
		saveDataToFile(file);
		return data;
	}
	
	protected static String getFileName(String marketplaceName, String period, String chain) {
		return PATHSAVEFILE + "\\" + marketplaceName + "_" + period + "_" + chain + ".json";
	}
	
	protected static boolean isGet(JsonObject jObject ,String property) {
		if(!jObject.has(property) || jObject.get(property).isJsonNull()) {			
			return false;
		}
		return true;
	}
	
	protected static String getResponeRequest(HttpRequest request) {
		HttpResponse<String> res = null;
		try {
			res = HttpClient.newHttpClient()
						.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 return res.body();
	}
	
	protected static String getTimeCrawl(String format) {
		LocalDateTime time = LocalDateTime.now();
		return time.format(DateTimeFormatter.ofPattern(format));
	}

}
