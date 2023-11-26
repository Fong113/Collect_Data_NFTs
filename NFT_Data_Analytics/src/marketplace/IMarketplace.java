package marketplace;

import marketplace.crawl.ChainType;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.PeriodType;
import marketplace.handle.Collection;
import marketplace.handle.Trending;

public interface IMarketplace {
	Trending getTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period, int row);
	Collection getCollection(String collectionName, ChainType chain, PeriodType period);
	void crawlAllData();
	void clearData();
}