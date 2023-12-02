package twitter.crawl;

import twitter.crawl.selenium.HandleSeleniumCrawl;

public class RunCrawler {

	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "D:\\Code_for_study\\SeleniumSetup\\chromedriver-win64\\chromedriver.exe");
		ICrawler takeData = new HandleSeleniumCrawl();
		takeData.loginTwitter();
		takeData.getTweetsNFTs();
		
	}
}
