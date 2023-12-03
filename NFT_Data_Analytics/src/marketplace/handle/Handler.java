package marketplace.handle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import marketplace.IMarketplace;
import marketplace.crawl.ChainType;
import marketplace.crawl.Crawler;
import marketplace.crawl.CrawlerFactory;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.PeriodType;

public class Handler implements IMarketplace {
	private Crawler crawler;
	
	@Override
	public Trending getTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period, int rows) {
		String chainStr = chain.getValue();
		String periodStr = period.getValue();
		
		crawler = CrawlerFactory.getCrawler(marketplaceType, chain, period, rows);
		
		JsonObject data = crawler.crawlData();
		String createdAt = data.get("createdAt").getAsString();
		String currency = data.get("currency").getAsString();
		ArrayList<Collection> colList = new ArrayList<Collection>();
		
		for(JsonElement e : data.getAsJsonArray("data")) {
			Collection col = new Gson().fromJson(e, Collection.class);
			colList.add(col);
		}
		
		return new Trending(marketplaceType, createdAt, chainStr, periodStr, currency, colList);
	}

	@Override
	public Set<CollectionFilter> getCollectionList(String collectionName) {
		
		File filesList[] = Crawler.folderOfMarketplace.listFiles();
		JsonObject data = null;
		Set<CollectionFilter> result = new HashSet<CollectionFilter>();
		
		for(File f : filesList) {
			try (Scanner sc = new Scanner(f)) {
				data = JsonParser.parseString(sc.nextLine()).getAsJsonObject();
				
				if(!data.get("data").isJsonNull()) {
					for(JsonElement e : data.get("data").getAsJsonArray()) {
						if(e.getAsJsonObject().get("name").getAsString().contains(collectionName)) {
							CollectionFilter col = new Gson().fromJson(e, CollectionFilter.class);
							col.setMarketPlaceName(data.get("marketplaceName").getAsString());
							col.setChain(data.get("chain").getAsString());
							col.setPeriod(data.get("period").getAsString());
							result.add(col);
						}
					}
				}
				
			} catch (IOException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void crawlAllData() {
		Crawler.crawlAllData();
		
	}

	@Override
	public void clearData() {
		Crawler.clearAllData();
		
	}

	
	@Override
	public Set<String> getCollectionNameList() {
		Set<String> result = new HashSet<String>();
		File filesList[] = Crawler.folderOfMarketplace.listFiles();
		JsonObject data = null;
		
		for(File f : filesList) {
			try (Scanner sc = new Scanner(f)) {
				data = JsonParser.parseString(sc.nextLine()).getAsJsonObject();
				
				if(!data.get("data").isJsonNull()) {
					for(int i = 0; i < 2; i++) {						
						result.add(data.getAsJsonArray("data").get(i).getAsJsonObject().get("name").getAsString());
					}
				}
				
			} catch (IOException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
