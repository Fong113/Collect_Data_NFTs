package twitter.crawl;

import twitter.crawl.selenium.HandleSeleniumCrawl;

public class RunCrawler {

	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver-win64\\chromedriver.exe");
		ICrawler takeData = new HandleSeleniumCrawl();
		takeData.loginTwitter();
		takeData.getTweetsNFTs();
		
		
		String[] nfts = { "Arbitrum Odyssey NFT", "Lil Pudgys"};
		for (String string : nfts) {
			takeData.getTweetsByNameNFTs(string);
		}
	}
}
