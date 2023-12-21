package crawler.twitter;

import java.util.List;

import helper.exception.InternetConnectionException;
import model.Tweet;

public interface ICrawler {

	List<Tweet> crawlTweetsAboutNFTs() throws InternetConnectionException, InterruptedException;

	List<Tweet> crawlTweetsByNameNFTs(String nameNFTs) throws InterruptedException, InternetConnectionException;
	
	void refreshData() throws  InternetConnectionException, InterruptedException;
}
