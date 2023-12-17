package test;

<<<<<<< HEAD
import java.util.List;
import java.util.Set;

import marketplace.IMarketplace;
import marketplace.crawl.CrawlerManager;
import marketplace.crawl.ICrawlerManager;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.handler.DataNotFoundException;
import marketplace.handler.MarketplaceHandler;
import marketplace.model.Collection;
import marketplace.model.CollectionFilter;
import marketplace.model.Trending;

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
		List<String> listMarketplace = MarketplaceType.getListMarktplaceName();
		List<String> listChains = MarketplaceType.OPENSEA.getListChains();
		List<String> listPeriods = MarketplaceType.OPENSEA.getListPeriods();
		
		for(String l: listChains) {
			System.out.println(l);
		}

//		try {
//			Trending t = m.getTrending(MarketplaceType.valueOf(listMarketplace.get(0)), listChains.get(0),
//					listPeriods.get(0));
//			System.out.println(listChains.get(0));
//			System.out.println(listPeriods.get(0));
//			for (Collection c : t.getData()) {
//				System.out.println(c.toString());
//			}
//
//			Set<CollectionFilter> cs = m.filterCollectionListByName("A");
//
//			for (CollectionFilter cf : cs) {
//				System.out.println(cf.toString());
//			}
//		} catch (DataNotFoundException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

=======
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import twitter.ITwitter;
import twitter.handle.HandleTwitter;
import twitter.handle.AHandle.TimePeriodType;
import twitter.handle.Tweet;


public class Test {
	public static void main(String[] args) throws InterruptedException {

		ITwitter t = new HandleTwitter();
//		t.refreshData();
		
		List<Tweet> tweets = t.getTweetsByNameNFTs("phong");
		
		for(Tweet test : tweets) {
			System.out.println(Arrays.toString(test.getTags().stream().toArray()));
		}
		
		
//		List<String> tags = t.getHotTags(TimePeriodType.DAILY);
//		int i = 0;
//		for(String test : tags) {
//			System.out.println(i + " " + test);
//			i++;
//		}

		
>>>>>>> fc31e9111e4728ddccb9f5a4161b228bec727a1f
	}
}
