package crawler.blog_news;

import java.io.IOException;

import org.openqa.selenium.TimeoutException;

public interface ICrawler {
	void crawl() throws IOException, TimeoutException, RuntimeException, InterruptedException;
}
