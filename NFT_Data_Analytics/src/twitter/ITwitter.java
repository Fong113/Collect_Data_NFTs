package twitter;

import java.util.List;

import twitter.handle.Tweet;

public interface ITwitter {
	List<Tweet> getTweetsNFTs();

	List<Tweet> getTweetsByNameNFTs(String collectionName);

	List<String> getTrending();
	
	List<Tweet> getTweetsByTag(String tag);

}
