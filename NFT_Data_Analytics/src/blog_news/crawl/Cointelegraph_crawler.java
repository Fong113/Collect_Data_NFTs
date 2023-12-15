package blog_news.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import com.google.gson.reflect.TypeToken;

import blog_news.Article;
import blog_news.helper.DateIO;
import blog_news.helper.JsonIO;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Cointelegraph_crawler implements ICrawler {
    	private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
    	String baseUrl = "https://cointelegraph.com/tags/nft";

  
    	@Override
    	public void crawl() throws IOException, TimeoutException {
    	    List<Article> existingArticles = Article_IO.loadJson(Article.getPATH());

    	    if (existingArticles == null || existingArticles.isEmpty()) {
    	        // Nếu danh sách là null hoặc trống, khởi tạo danh sách mới
    	        existingArticles = new ArrayList<>();
    	    } else {
                // Reset idCounter based on the maximum ID in existing articles
                int maxId = existingArticles.stream().mapToInt(Article::getId).max().orElse(0);
                Article.resetIdCounter(maxId);
            }

    	    List<Article> crawledArticles = crawlCoinTelegraph(existingArticles);
    	    Article_IO.writeToJson(crawledArticles, Article.getPATH());
    	}

    	private List<Article> crawlCoinTelegraph(List<Article> existingArticles) throws IOException, TimeoutException{
    		WebDriverManager.chromedriver().setup();
	    	ChromeOptions opt = new ChromeOptions();
			opt.setPageLoadStrategy(PageLoadStrategy.EAGER);
			opt.addArguments("--headless");
			opt.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
	    	WebDriver driver = new ChromeDriver(opt);
	        driver.manage().window().maximize();
	        driver.get(baseUrl);
//	        for (int i = 1; i <= 2; i++) {
	                	((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	                	((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -500);");
	                	try {
	                		Thread.sleep(200); // Tạm dừng để đợi dữ liệu mới tải
	                	} catch (InterruptedException e) {
	                		e.printStackTrace();
	                	}
//	                }
	    	try {
	                Document doc = Jsoup.parse(driver.getPageSource());
	                Elements articles = doc.select("article");
	                
	                for (Element article : articles) {
	                    Article currentArticle = new Article();
	
	                    currentArticle.setTitle(article.select("div.post-card-inline__header a.post-card-inline__title-link span.post-card-inline__title").text());
	                    currentArticle.setAbsoluteURL("https://cointelegraph.com" + article.select("a").attr("href"));
	
	                    Document articleDoc = Jsoup.connect(currentArticle.getAbsoluteURL()).get();
	
	                    // Lấy nội dung bài viết
	                    String leadtext = articleDoc.select("div.post__block_lead-text p.post__lead").text();
	                    Elements content = articleDoc.select("div.post-content p:not(:has(strong em a))");
	                    String combinedContent = content.text();
	                    String fullContent = leadtext + "\n" + combinedContent;
	                    currentArticle.setFullContent(fullContent);
	
	                    // Lấy tags/keywords
	                    Elements tags = articleDoc.select("div.post__block_tags ul.tags-list__list li.tags-list__item a.tags-list__link span.tags-list_hash-sign ");
	                    List<String> tagList = new ArrayList<>();
	                    for (Element tagElement : tags) {
	                        String tagText = tagElement.parent().text();
	                        if (tagText.startsWith("#")) {
	                            tagList.add(tagText);
	                        }
	                    }
	                    currentArticle.setTags(tagList);
	
	                    // Lấy ngày xuất bản
	                    String publishDateStr = articleDoc.select("div.post-meta__publish-date time").attr("datetime");
	                    if (!publishDateStr.isEmpty()) {
	                        currentArticle.setPublishDate(DateIO.formatDate(publishDateStr));
	                    }
	
	                    existingArticles.add(currentArticle);
	
	                    System.out.println("Processed article: " + currentArticle.getTitle());
	                }
	                	                
	    	} catch (IOException e) {
	            // Ném lại IOException với thông báo riêng
	            throw new IOException("Lỗi network");
	        } catch (TimeoutException te) {
	            // Ném lại TimeoutException với thông báo riêng
	            throw new TimeoutException("Timeout trong lúc crawl");
	        } finally {
	            // Đóng trình duyệt
	        	System.out.println("Crawl https://cointelegraph.com/tags/nft done!!!");
	            driver.quit();
	        }
	    	return existingArticles;
    	}
}
