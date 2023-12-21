package twitter.interfaceTwitter;

import java.util.List;

import twitter.handle.AHandle.TimePeriodType;
import twitter.helper.exception.InternetConnectionException;
import twitter.model.Tweet;

public interface ITwitter {
	
	List<Tweet> getTweetsAboutNFTs();

	List<Tweet> getTweetsByNameNFTs(String collectionName) throws InterruptedException;
	
	List<Tweet> getTweetsByTag(String tag);

	List<String> getHotTags(TimePeriodType periodType);
	
	void refreshData() throws  InternetConnectionException, InterruptedException;
}
