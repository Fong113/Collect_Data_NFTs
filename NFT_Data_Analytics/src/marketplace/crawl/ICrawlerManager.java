package marketplace.crawl;

public interface ICrawlerManager {
	void crawlAllTrending();
	String getFileSaveData(MarketplaceType marketplaceType, ChainType chain, PeriodType period);
	String getPathSaveData();
}
