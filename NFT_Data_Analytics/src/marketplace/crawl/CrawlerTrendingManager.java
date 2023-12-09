package marketplace.crawl;

import java.util.ArrayList;
import java.util.List;

import marketplace.crawl.binance.Binance;
import marketplace.crawl.binance.BinanceChainType;
import marketplace.crawl.binance.BinancePeriodType;
import marketplace.crawl.niftygateway.Niftygateway;
import marketplace.crawl.niftygateway.NiftygatewayChainType;
import marketplace.crawl.niftygateway.NiftygatewayPeriodType;
import marketplace.crawl.opensea.Opensea;
import marketplace.crawl.opensea.OpenseaChainType;
import marketplace.crawl.opensea.OpenseaPeriodType;
import marketplace.crawl.rarible.Rarible;
import marketplace.crawl.rarible.RaribleChainType;
import marketplace.crawl.rarible.RariblePeriodType;

public class CrawlerTrendingManager implements ICrawlerManager{	
	public CrawlerTrendingManager() {}
	
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
	
	private void crawlAllTrendingofMarketplace(MarketplaceType marketplaceType) {
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
				crawler.crawlTrendingAndSaveToFile();
			}
	}
	
	@Override
	public void crawlAllTrending() {
		for(MarketplaceType m : MarketplaceType.values()) {
			crawlAllTrendingofMarketplace(m);
		}
	}
	
	@Override
	public String getFilename(MarketplaceType marketplaceType, ChainType chain, PeriodType period) {
		return Crawler.getFileName(marketplaceType.getValue(), chain.getValue(), period.getValue());
	}
}
