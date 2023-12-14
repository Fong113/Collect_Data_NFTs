package marketplace;

import java.util.Set;

import marketplace.crawl.ChainType;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.PeriodType;
import marketplace.handle.Collection;
import marketplace.handle.CollectionFilter;
import marketplace.handle.Trending;

public interface IMarketplace {
	Trending getTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period, int rows);
	Set<CollectionFilter> getCollectionList(String collectionName);
	Set<String> getCollectionNameList();
	void crawlAllData();
	void clearData();
}