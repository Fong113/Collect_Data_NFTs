package twitter;

import java.util.List;
import twitter.handle.Tweet;
import twitter.handle.HandleTwitter.TimePeriodType;

public interface ITwitter {
	
	List<Tweet> getTweetsAboutNFTs();

	List<Tweet> getTweetsByNameNFTs(String collectionName);
	
	List<Tweet> getTweetsByTag(String tag);

	List<String> getHotTags(TimePeriodType periodType);
	
	void refreshData();
}
