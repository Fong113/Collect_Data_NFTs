package blog_news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import com.google.gson.reflect.TypeToken;

import blog_news.helper.DateIO;
import blog_news.helper.JsonIO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Cointelegraph_crawler implements ICrawler {
    	private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
    	private final String PATH = "E:\\NFTs\\BTL.OOP.GROUP24\\NFT_Data_Analytics\\data\\cointelegraph_news.json";
    	String baseUrl = "https://cointelegraph.com/tags/nft";
    	
    	@Override
    	public void crawl() {		
    		Article_IO.writeToJson(crawlCoinTelegraph(), PATH);
    	}
    	
    	private List<Article> crawlCoinTelegraph(){
	    	System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");
	    	WebDriver driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.get(baseUrl);
	        List<Article> articleList = new ArrayList<>();
	        for (int i = 0; i <= 5; i++) {
	                	((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	                	((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -500);");
	                	try {
	                		Thread.sleep(2000); // Tạm dừng để đợi dữ liệu mới tải
	                	} catch (InterruptedException e) {
	                		e.printStackTrace();
	                	}
	                }
	    	try {
	                Document doc = Jsoup.parse(driver.getPageSource());
	                Elements articles = doc.select("article");
	
	                if (articles.isEmpty()) {
	                    System.out.println("No more articles. Crawling completed.");
	                }
	
	                
	                
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
	
	                    articleList.add(currentArticle);
	
	                    System.out.println("Processed article: " + currentArticle.getTitle());
	                }
	                	                
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            // Đóng trình duyệt
	        	System.out.println("Crawl https://cointelegraph.com/tags/nft done!!!");
	            driver.quit();
	        }
	    	return articleList;
    	}
    	
}
