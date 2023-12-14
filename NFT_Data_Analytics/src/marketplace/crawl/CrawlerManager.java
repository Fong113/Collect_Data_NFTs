package marketplace.crawl;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.binance.Binance;
import marketplace.crawl.binance.BinanceChainType;
import marketplace.crawl.binance.BinancePeriodType;
import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.crawl.niftygateway.Niftygateway;
import marketplace.crawl.niftygateway.NiftygatewayChainType;
import marketplace.crawl.niftygateway.NiftygatewayPeriodType;
import marketplace.crawl.opensea.Opensea;
import marketplace.crawl.opensea.OpenseaChainType;
import marketplace.crawl.opensea.OpenseaPeriodType;
import marketplace.crawl.rarible.Rarible;
import marketplace.crawl.rarible.RaribleChainType;
import marketplace.crawl.rarible.RariblePeriodType;
import marketplace.crawl.type.ChainType;
import marketplace.crawl.type.MarketplaceType;
import marketplace.crawl.type.PeriodType;

public class CrawlerManager implements ICrawlerManager{	
	public CrawlerManager() {}
	
	private static final Crawler getCrawler(MarketplaceType marketplaceType, String chain, String period) {
		switch(marketplaceType) {
		case BINANCE:
			 return new Binance(chain, period);
		case OPENSEA:
			 return new Opensea(chain, period);
		case NIFTYGATEWAY:
			 return new Niftygateway(chain, period);
		case RARIBLE:
			 return new Rarible(chain, period);
		}
		return null;
	}
	
	private void crawlAllTrendingofMarketplace(MarketplaceType marketplaceType) throws CrawlTimeoutException, InternetConnectionException, Exception {
		List<String> chains = new ArrayList<String>();
		List<String> periods = new ArrayList<String>();
		switch(marketplaceType) {
		case BINANCE:
			 chains.addAll(BinanceChainType.getValues());
			 periods.addAll(BinancePeriodType.getValues());
			 break;
		case OPENSEA:
			 chains.addAll(OpenseaChainType.getValues());
			 periods.addAll(OpenseaPeriodType.getValues());
			 break;
		case NIFTYGATEWAY:
			 chains.addAll(NiftygatewayChainType.getValues());
			 periods.addAll(NiftygatewayPeriodType.getValues());
			 break;
		case RARIBLE:
			 chains.addAll(RaribleChainType.getValues());
			 periods.addAll(RariblePeriodType.getValues());
			 break;
		}
		
		for(String chain : chains) 
			for(String period : periods) {
				Crawler crawler = getCrawler(marketplaceType, chain, period);
				try {
					crawler.crawlTrendingAndSaveToFile();					
				} catch (CrawlTimeoutException e) {
					System.out.println(marketplaceType.getValue() + " " + chain + " " + period + " time out");
					throw e;
				}
			}
	}
	
	@Override
	public void crawlAllTrending() throws CrawlTimeoutException, InternetConnectionException, Exception {
		for(MarketplaceType m : MarketplaceType.values()) {
			crawlAllTrendingofMarketplace(m);				
		}
	}
	
	@Override
	public String getFileSaveData(MarketplaceType marketplaceType, ChainType chain, PeriodType period) {
		return Crawler.getFileSaveData(marketplaceType.getValue(), period.getValue(), chain.getValue());
	}
	
	@Override
	public String getPathSaveData() {
		return Crawler.PATHSAVEDATA;
	}
}
