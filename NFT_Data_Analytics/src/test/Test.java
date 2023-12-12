package test;

import java.util.Set;

import marketplace.IMarketplace;
import marketplace.crawl.CrawlerManager;
import marketplace.crawl.ICrawlerManager;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.opensea.OpenseaChainType;
import marketplace.crawl.opensea.OpenseaPeriodType;
import marketplace.handler.MarketplaceHandler;
import marketplace.model.Collection;
import marketplace.model.CollectionFilter;
import marketplace.model.Trending;

public class Test {
	public static void main(String[] args) {
		ICrawlerManager test = new CrawlerManager();
		test.crawlAllTrending();

		IMarketplace m = new MarketplaceHandler();
		
		Trending t =  m.getTrending(MarketplaceType.OPENSEA, OpenseaChainType.ETH, OpenseaPeriodType.ONEDAY);
		
		for(Collection c : t.getData()) {
			System.out.println(c.toString());
		}
		
//		Set<CollectionFilter> cs = m.filterCollectionListByName("A");
//		
//		for(CollectionFilter cf : cs) {
//			System.out.println(cf.toString());
//		}
	}
}
