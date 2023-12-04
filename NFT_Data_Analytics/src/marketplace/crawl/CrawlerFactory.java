package marketplace.crawl;

import marketplace.crawl.binance.Binance;
import marketplace.crawl.niftygateway.Niftygateway;
import marketplace.crawl.opensea.Opensea;
import marketplace.crawl.rarible.Rarible;

public class CrawlerFactory {
	private CrawlerFactory() {}
	
	public static final Crawler getCrawler(MarketplaceType marketplaceType, ChainType chain, PeriodType period, int rows) {
		switch(marketplaceType) {
		case BINANCE:
			return new Binance(chain.getValue(), period.getValue(), rows);
		case OPENSEA:
			return new Opensea(chain.getValue(), period.getValue(), rows);
		case NIFTYGATEWAY:
			return new Niftygateway(chain.getValue(), period.getValue(), rows);
		case RARIBLE:
			return new Rarible(chain.getValue(), period.getValue(), rows);
		default:
			return null;
		}
	}
}
