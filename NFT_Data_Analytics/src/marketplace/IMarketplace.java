package marketplace;

import java.util.Set;

import marketplace.crawl.ChainType;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.PeriodType;
import marketplace.model.CollectionFilter;
import marketplace.model.Trending;

public interface IMarketplace {
	Trending getTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period);
	Set<CollectionFilter> filterCollectionListByName(String collectionName);
	Set<String> getCollectionNameList();
	void clearData();
}