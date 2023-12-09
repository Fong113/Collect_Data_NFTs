package test;

import marketplace.crawl.CrawlerTrendingManager;
import marketplace.crawl.ICrawlerManager;

public class Test {
	public static void main(String[] args) {
		ICrawlerManager test = new CrawlerTrendingManager();
		test.crawlAllTrending();
	}
}
