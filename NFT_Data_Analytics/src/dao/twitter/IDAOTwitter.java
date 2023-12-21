package dao.twitter;

import java.util.List;

import handle.twitter.AHandle.TimePeriodType;
import helper.exception.InternetConnectionException;
import model.Tweet;

public interface IDAOTwitter {
	
	List<Tweet> getTweetsAboutNFTs();

	List<Tweet> getTweetsByNameNFTs(String collectionName) throws InterruptedException;
	
	List<Tweet> getTweetsByTag(String tag);

	List<String> getHotTags(TimePeriodType periodType);
	
}
