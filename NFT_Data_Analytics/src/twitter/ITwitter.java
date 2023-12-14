package twitter;

import java.util.List;
import twitter.handle.Tweet;
import twitter.handle.Handle.PeriodType;

public interface ITwitter {
	List<Tweet> getTweetsNFTs();

	List<Tweet> getTweetsByNameNFTs(String collectionName);
	
	List<Tweet> getTweetsByTag(String tag);

	List<String> getHotTags(PeriodType periodType);
}
