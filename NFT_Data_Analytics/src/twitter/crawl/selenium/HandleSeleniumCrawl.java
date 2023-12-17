package twitter.crawl.selenium;

import java.util.List;
import twitter.crawl.ICrawler;
import twitter.handle.Tweet;

public class HandleSeleniumCrawl extends SeleniumCrawl implements ICrawler {

	@Override
	public void loginTwitter() {
		visitWebsite("https://twitter.com/i/flow/login");
		enterUsername("fongdo113");
		enterPassword("Phong@2003");
	};

	@Override
	public List<Tweet> crawlTweetsAboutNFTs() throws InterruptedException {
		int  tweetsQuantity = 20;
		loginTwitter();
		searchByTag("(nft OR nfts) (#nft OR #nfts)");
		
		List<Tweet> tweetList = getArrayTweetList(tweetsQuantity);
		putToFile("NFTs", tweetList);
		quitTwitter();

		return tweetList;
	};

	@Override
	public List<Tweet> crawlTweetsByNameNFTs(String nameNFTs) throws InterruptedException {
		int tweetsQuantity = 10;
		loginTwitter();
		searchByTag(nameNFTs);
		List<Tweet> tweetList = getArrayTweetList(tweetsQuantity);
		quitTwitter();

		return tweetList;
	};
}
