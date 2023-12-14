package marketplace.crawl;

import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.crawl.type.ChainType;
import marketplace.crawl.type.MarketplaceType;
import marketplace.crawl.type.PeriodType;

public interface ICrawlerManager {
	void crawlAllTrending() throws CrawlTimeoutException, InternetConnectionException, Exception;
	String getFileSaveData(MarketplaceType marketplaceType, ChainType chain, PeriodType period);
	String getPathSaveData();
}
