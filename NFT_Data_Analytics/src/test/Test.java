package test;

import marketplace.crawl.CrawlerManager;
import marketplace.crawl.ICrawlerManager;
import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.crawl.opensea.OpenseaChainType;
import marketplace.crawl.opensea.OpenseaPeriodType;
import marketplace.crawl.type.MarketplaceType;
import marketplace.handler.DataNotFoundException;
import marketplace.handler.MarketplaceHandler;
import marketplace.model.Collection;
import marketplace.model.Trending;

import marketplace.IMarketplace;

public class Test {
	public static void main(String[] args) {
		ICrawlerManager test = new CrawlerManager();
		
        long startTime = System.currentTimeMillis();
		try {
			test.crawlAllTrending();
		} catch (CrawlTimeoutException e) {
			System.out.println(e.getMessage());
		} catch (InternetConnectionException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

		System.out.println(elapsedTime);
		
		IMarketplace m = new MarketplaceHandler();
		
		try {
			Trending t =  m.getTrending(MarketplaceType.OPENSEA, OpenseaChainType.BNB, OpenseaPeriodType.ONEDAY);
			for(Collection c : t.getData()) {
				System.out.println(c.toString());
			}
		}
		
		catch (DataNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			m.clearData();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		

		
//		Set<CollectionFilter> cs = m.filterCollectionListByName("A");
//		
//		for(CollectionFilter cf : cs) {
//			System.out.println(cf.toString());
//		}
	}
}
