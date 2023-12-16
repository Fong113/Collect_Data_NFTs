package marketplace;

import java.io.IOException;
import java.util.Set;

import com.google.gson.JsonSyntaxException;

import marketplace.crawl.MarketplaceType;
import marketplace.model.CollectionFilter;
import marketplace.model.Trending;

public interface IMarketplace {
	Trending getTrending(MarketplaceType marketplaceType, String chain, String period) throws Exception;
	Set<CollectionFilter> filterCollectionListByName(String collectionName) throws IOException, JsonSyntaxException;
	void clearData() throws IOException;
}