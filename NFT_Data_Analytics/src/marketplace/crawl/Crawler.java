package marketplace.crawl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public abstract class Crawler {
	protected String period;
	protected String chain;
	protected String respone;
	protected int rows;
	protected JsonObject data = new JsonObject();	
	
	protected abstract void getRespone();
	
	protected abstract void preprocessData();
	
	
	protected void saveToFile(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(data.toString());
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public JsonObject crawlData() {
		File file = new File(getFileName());
		
		if(file.isFile()) {
			try (Scanner sc = new Scanner(file)) {
				data = JsonParser.parseString(sc.nextLine()).getAsJsonObject();
			} catch (IOException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			getRespone();
			preprocessData();
			saveToFile(file);
		}
		
		return data;
	}
	
	protected abstract String getFileName();
	
	
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
	
	protected static String getTime(String format) {
		LocalDateTime time = LocalDateTime.now();
		return time.format(DateTimeFormatter.ofPattern(format));
	}
}
