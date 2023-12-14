package blog_news.crawl;

import blog_news.helper.JsonIO;

public class HandleBlognewsCrawler implements ICrawler {

	@Override
	public void crawl() {
		JsonIO.clearBlogNewsData();
		new Todaynftnews_crawler().crawl();
		new Cointelegraph_crawler().crawl();
	}
	public static void main(String[] args) {
		new HandleBlognewsCrawler().crawl();
		
	}

}
