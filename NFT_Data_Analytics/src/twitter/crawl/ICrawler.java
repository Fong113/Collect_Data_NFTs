package twitter.crawl;

import java.util.List;

import twitter.helper.exception.InternetConnectionException;
import twitter.model.Tweet;

public interface ICrawler {

	List<Tweet> crawlTweetsAboutNFTs() throws InternetConnectionException, InterruptedException;

	List<Tweet> crawlTweetsByNameNFTs(String nameNFTs) throws InterruptedException, InternetConnectionException;

}
