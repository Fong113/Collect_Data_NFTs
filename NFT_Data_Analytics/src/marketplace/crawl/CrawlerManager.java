package marketplace.crawl;

import java.util.List;

import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;

public class CrawlerManager implements ICrawlerManager{	
	public CrawlerManager() {}
	
	private static final Crawler getCrawler(MarketplaceType marketplaceType) {
		switch(marketplaceType) {
		case BINANCE:
			 return new Binance();
		case OPENSEA:
			 return new Opensea();
		case NIFTYGATEWAY:
			 return new Niftygateway();
		case RARIBLE:
			 return new Rarible();
		}
		return null;
	}
	
	@Override
	public void crawlAllTrending() throws CrawlTimeoutException, InternetConnectionException, Exception {
		for(MarketplaceType m : MarketplaceType.values()) {
			List<String> chains = m.getListChains();
			List<String> periods = m.getListPeriods();
			Crawler crawler = getCrawler(m);
			
			for(String chain : chains) 
				for(String period : periods) {
					try {
						crawler.setChain(chain);
						crawler.setPeriod(period);
						crawler.crawlTrendingAndSaveToFile();					
					} catch (CrawlTimeoutException e) {
						System.out.println(m + " " + chain + " " + period + " time out");
						throw e;
					}
				}			
		}
	}
	
	@Override
	public String getFileSaveData(MarketplaceType marketplaceType, String chain, String period) {
		return Crawler.getFileSaveData(marketplaceType.name(), period, chain);
	}
	
	@Override
	public String getPathSaveData() {
		return Crawler.PATHSAVEDATA;
	}
}
