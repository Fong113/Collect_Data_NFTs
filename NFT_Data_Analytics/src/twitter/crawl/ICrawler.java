package twitter.crawl;

import java.util.List;
import twitter.handle.Tweet;

public interface ICrawler {

	void loginTwitter();

	List<Tweet> crawlTweetsAboutNFTs() throws InterruptedException;

	List<Tweet> crawlTweetsByNameNFTs(String nameNFTs) throws InterruptedException;

}
