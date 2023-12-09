package marketplace.crawl;

public interface ICrawlerManager {
	void crawlSingleTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period);
	void crawlAllTrendingofMarketplace(MarketplaceType marketplaceType);
	void crawlAllTrending();
	String getFilename(MarketplaceType marketplaceType, ChainType chain, PeriodType period);
}
