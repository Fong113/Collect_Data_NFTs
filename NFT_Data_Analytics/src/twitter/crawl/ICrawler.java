package twitter.crawl;
import twitter.handle.Collection;

public interface ICrawler {
	
	void loginTwitter();
	Collection[] getTweetsNFTs();
	Collection[] getTweetsByNameNFTs(String nameNFTs);
}
