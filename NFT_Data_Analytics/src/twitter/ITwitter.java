package twitter;

import java.util.List;
import twitter.handle.Tweet;
import twitter.handle.AHandle.TimePeriodType;

public interface ITwitter {
	
	List<Tweet> getTweetsAboutNFTs();

	List<Tweet> getTweetsByNameNFTs(String collectionName) throws InterruptedException;
	
	List<Tweet> getTweetsByTag(String tag);

	List<String> getHotTags(TimePeriodType periodType);
	
	void refreshData() throws InterruptedException;
}
