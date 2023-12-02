package twitter;

import twitter.handle.Collection;

public interface ITwitter {
	Collection[] getTweetNFTs();
	Collection getTweetByTag(String collectionName);
	Collection getTrendingTags();
	
}
