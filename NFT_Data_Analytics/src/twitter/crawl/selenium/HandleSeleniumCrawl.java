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
		
		ArrayList<Collection> tweetList = new ArrayList<>();
		
		searchByTag("(nft OR nfts) (#nft OR #nfts)");
		scrollDown();
		
        List<WebElement> articleList = searchResults();
   
        int i = 0;
		for(WebElement tweet : articleList) {
			i++;
            String author = getAuthor(tweet);
            
            String[] tags = getTag(tweet);
  
        	String content = getContent(tweet);
        	
        	String imageURL = getImageURL(tweet);
        	
        	String date = getTimePost(tweet);
        	
        	tweetList.add( new Collection(i,author, content, tags, imageURL, date ));

		}
		
		putToFile("NFTs.json", tweetList);
		
		Collection [] tweets = new Collection[tweetList.size()];
		tweets = tweetList.toArray(tweets);
		
		return tweets;
	};
	public Collection[]  getTweetsByNameNFTs(String nameNFTs) {
		return null;
	};
}
