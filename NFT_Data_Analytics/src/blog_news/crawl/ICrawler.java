package blog_news.crawl;

import java.io.IOException;

import org.openqa.selenium.TimeoutException;

public interface ICrawler {
	void crawl() throws IOException, TimeoutException, RuntimeException, InterruptedException;
}
