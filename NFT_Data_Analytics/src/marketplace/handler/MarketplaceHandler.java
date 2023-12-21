package marketplace.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import marketplace.IMarketplace;
import marketplace.crawl.CrawlerManager;
import marketplace.crawl.ICrawlerManager;
import marketplace.crawl.MarketplaceType;
import marketplace.model.Collection;
import marketplace.model.CollectionFilter;
import marketplace.model.Trending;

public class MarketplaceHandler implements IMarketplace {
	
	private ICrawlerManager crawlerManager;
	private File DB;
	
	public MarketplaceHandler() {
		crawlerManager = new CrawlerManager();
		DB = new File(crawlerManager.getPathSaveData()); 
	}

	@Override
	public Trending getTrending(MarketplaceType marketplaceType, String chain, String period) throws DataNotFoundException, Exception {
		File fileSaveTrendingData = new File(crawlerManager.getFileSaveData(marketplaceType, chain, period));
		
		try(Scanner sc = new Scanner(fileSaveTrendingData)) {
			ArrayList<Collection> colList = new ArrayList<Collection>();
			JsonObject data = JsonParser.parseString(sc.nextLine()).getAsJsonObject();
				
			String createdAt = data.get("createdAt").getAsString();
			String currency = data.get("currency").getAsString();
				
			for(JsonElement e : data.getAsJsonArray("data")) {
				Collection col = new Gson().fromJson(e, Collection.class);
				colList.add(col);
			}
			
			return new Trending(marketplaceType.name(), createdAt, chain, period, currency, colList);			
		} catch (FileNotFoundException | NoSuchElementException e) {
			throw new DataNotFoundException("Data not found", e);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Set<CollectionFilter> filterCollectionListByName(String collectionName) throws IOException, JsonSyntaxException {
		File filesList[] = DB.listFiles();
		JsonObject data = null;
		Set<CollectionFilter> result = new HashSet<CollectionFilter>();
		
		for(File f : filesList) {
			try (Scanner sc = new Scanner(f)) {
				data = JsonParser.parseString(sc.nextLine()).getAsJsonObject();
				
				if(!data.get("data").isJsonNull()) {
					for(JsonElement e : data.get("data").getAsJsonArray()) {
						if(e.getAsJsonObject().get("name").getAsString().contains(collectionName)) {
							CollectionFilter col = new Gson().fromJson(e, CollectionFilter.class);
							col.setMarketplaceName(data.get("marketplaceName").getAsString());
							col.setChain(data.get("chain").getAsString());
							col.setPeriod(data.get("period").getAsString());
							col.setCurrency(data.get("currency").getAsString());
							result.add(col);
						}
					}
				}
				
			} catch (IOException | JsonSyntaxException e) {
				throw e;
			}
		}
		return result;
	}

	@Override
	public void clearData() throws IOException{
		try {
			FileUtils.cleanDirectory(DB);
			System.out.println("Clear data success !");
		} catch (IOException e) {
			throw e;
		}
	}
	
}
