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
	public List<Tweet> crawlTweetsAboutNFTs() {

		searchByTag("(nft OR nfts) (#nft OR #nfts)");
		scrollDown();
		List<Tweet> tweetList = getArrayTweetList();
		putToFile("NFTs", tweetList);
		quitTwitter();

		return tweetList;
	};

	@Override
	public List<Tweet> crawlTweetsByNameNFTs(String nameNFTs) {
		loginTwitter();
		searchByTag(nameNFTs);
		scrollDown();
		List<Tweet> tweetList = getArrayTweetList();
		quitTwitter();

		return tweetList;
	};
}
