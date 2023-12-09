package marketplace.crawl;

public interface ICrawlerManager {
	void crawlAllTrending();
	String getFilename(MarketplaceType marketplaceType, ChainType chain, PeriodType period);
}
