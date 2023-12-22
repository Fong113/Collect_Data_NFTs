package blog_news.crawl;

import java.io.IOException;

import org.openqa.selenium.TimeoutException;

import blog_news.Article;
import blog_news.helper.JsonIO;

public class HandleBlognewsCrawler implements ICrawler {

	@Override
	public void crawl() throws IOException, TimeoutException, RuntimeException, InterruptedException {
		JsonIO.clearBlogNewsData(Article.getPATH());
//		new Todaynftnews_crawler().crawl();
		new CryptoNews_crawler().crawl();
	}
	public static void main(String[] args) throws IOException, TimeoutException, RuntimeException, InterruptedException {
		new HandleBlognewsCrawler().crawl();		
	}
}
