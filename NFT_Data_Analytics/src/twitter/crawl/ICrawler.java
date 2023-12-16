package twitter.crawl;

import java.util.List;
import twitter.handle.Tweet;

public interface ICrawler {

	void loginTwitter();

	List<Tweet> crawlTweetsAboutNFTs();

	List<Tweet> crawlTweetsByNameNFTs(String nameNFTs);

}
