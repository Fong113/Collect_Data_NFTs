package blog_news;

public class HandleBlognewsCrawler implements ICrawler {

	@Override
	public void crawl() {
		new Cointelegraph_crawler().crawl();
		new Todaynftnews_crawler().crawl();
	}
	public static void main(String[] args) {
		new HandleBlognewsCrawler().crawl();
		
	}

}
