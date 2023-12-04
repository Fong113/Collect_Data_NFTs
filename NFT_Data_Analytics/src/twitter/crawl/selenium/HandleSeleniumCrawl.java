package twitter.crawl.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import twitter.crawl.ICrawler;
import twitter.handle.Collection;


public class HandleSeleniumCrawl extends SeleniumCrawl implements ICrawler {
	
	@Override
	public void loginTwitter(){	
		visitWebsite("https://twitter.com/i/flow/login");
		enterUsername("fongdo113");
		enterPassword("Phong@2003");
	};

	@Override
	public Collection[]  getTweetsNFTs() {
		
		searchByTag("(nft OR nfts) (#nft OR #nfts)");
		scrollDown();
        List<WebElement> articleList = searchResults();
        ArrayList<Collection> tweetList = getArrayTweetList(articleList);
		putToFile("NFTs", tweetList);
		
		Collection [] tweets = new Collection[tweetList.size()];
		tweets = tweetList.toArray(tweets);
		return tweets;
	};
	
	@Override
	public Collection[]  getTweetsByNameNFTs(String nameNFTs) {
		searchByTag(nameNFTs);
		scrollDown();
        List<WebElement> articleList = searchResults();
        ArrayList<Collection> tweetList = getArrayTweetList(articleList);
		putToFile(nameNFTs, tweetList);
		
		Collection [] tweets = new Collection[tweetList.size()];
		tweets = tweetList.toArray(tweets);
		return tweets;
	};
}
