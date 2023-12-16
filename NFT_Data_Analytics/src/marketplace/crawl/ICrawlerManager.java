package marketplace.crawl;

import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;

public interface ICrawlerManager {
	void crawlAllTrending() throws CrawlTimeoutException, InternetConnectionException, Exception;
	String getFileSaveData(MarketplaceType marketplaceType, String chain, String period);
	String getPathSaveData();
}
