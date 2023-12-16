package marketplace;

import java.io.IOException;
import java.util.Set;

import com.google.gson.JsonSyntaxException;

import marketplace.crawl.type.ChainType;
import marketplace.crawl.type.MarketplaceType;
import marketplace.crawl.type.PeriodType;
import marketplace.model.CollectionFilter;
import marketplace.model.Trending;

public interface IMarketplace {
	Trending getTrending(MarketplaceType marketplaceType, ChainType chain, PeriodType period) throws Exception;
	Set<CollectionFilter> filterCollectionListByName(String collectionName) throws IOException, JsonSyntaxException;
	Set<String> getCollectionNameList() throws IOException, JsonSyntaxException;
	void clearData() throws IOException;
}