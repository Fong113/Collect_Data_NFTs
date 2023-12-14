package twitter.crawl.selenium;

import java.util.ArrayList;
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
	public List<Tweet>  getTweetsNFTs() {

		searchByTag("(nft OR nfts) (#nft OR #nfts)");
		scrollDown();
		List<Tweet> tweetList = getArrayTweetList();
		
		putToFile("NFTs", tweetList);
	
		
		return tweetList;
	};

	@Override
	public List<Tweet> getTweetsByNameNFTs(String nameNFTs) {
		searchByTag(nameNFTs);
		scrollDown();
		
		ArrayList<Tweet> tweetList = getArrayTweetList();
		putToFile(nameNFTs, tweetList);
		
		return tweetList;
	};
}
