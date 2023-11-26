package marketplace.handle;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import marketplace.IMarketplace;
import marketplace.crawl.ChainType;
import marketplace.crawl.Crawler;
import marketplace.crawl.CrawlerFactory;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.PeriodType;

public class Handler implements IMarketplace {
	
	@Override
	public Trending getTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period, int rows) {
		String chainStr = chain.getValue();
		String periodStr = period.getValue();
		
		Crawler crawler = CrawlerFactory.getCrawler(marketplaceType, chain, period, rows);
		
		JsonObject data = crawler.crawlData();
		String createdAt = data.get("createdAt").getAsString();
		ArrayList<Collection> colList = new ArrayList<Collection>();
		
		for(JsonElement e : data.getAsJsonArray("data")) {
			Collection col = new Gson().fromJson(e, Collection.class);
			colList.add(col);
		}
		
		Trending trend = new Trending(marketplaceType, createdAt, chainStr, periodStr, colList);
		return trend;
	}

	@Override
	public Collection getCollection(String collectionName, ChainType chain, PeriodType period) {
		
		return null;
	}

	@Override
	public void crawlAllData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}

}
