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
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;



public class Todaynftnews_crawler implements ICrawler {
	private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
	final String PATH = "E:\\NFTs\\BTL.OOP.GROUP24\\NFT_Data_Analytics\\data\\todaynft_news.json";
	private String baseUrl = "https://www.todaynftnews.com/nft-news/";
	
	@Override
	public void crawl() {		
		Article_IO.writeToJson(crawlTodayNFTnews(), PATH);
	}
	
	private List<Article> crawlTodayNFTnews(){
    	System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");
    	WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        List<Article> articleList = new ArrayList<>();
        
    	try {
    		    for (int morePage = 1; morePage < 5; morePage++) {
    		    	 try {
    		    		 	Thread.sleep(1000);
    		    	        WebElement closeButton = driver.findElement(By.cssSelector(".pum-close.popmake-close"));
    		    	        closeButton.click();
    		    	        System.out.println("Popup đã được đóng.");
    		    	        Thread.sleep(1000);
    		    	        clickLoadMoreButton(driver);
    		    	    } catch (Exception e) {
    		    	        // Xử lý exception
    		    	        System.out.println("Popup không xuất hiện hoặc đã hết thời gian chờ.");
    		    	    } finally {
    		    	        // Thực hiện thao tác clickLoadMoreButton dù có exception hay không
    		    	        clickLoadMoreButton(driver);
    		    	    }
    		    }

                Document doc = Jsoup.parse(driver.getPageSource());
                Elements articles = doc.select("article");
                for (Element article : articles) {
                    Article currentArticle = new Article();

                    currentArticle.setTitle(article.select("h2[class='title front-view-title']").text());
                    currentArticle.setAbsoluteURL(article.select("a").attr("href"));

                    Document articleDoc = Jsoup.connect(currentArticle.getAbsoluteURL()).get();

                    // Lấy nội dung bài viết
                    String fullContent = articleDoc.select("div.thecontent").text();
                    currentArticle.setFullContent(fullContent);

                    // Lấy tags/keywords
                    Elements tags = articleDoc.select("div[class=tags] a");
                    List<String> tagList = new ArrayList<>();
                    for (Element tagElement : tags) {
                        String tagText = "#" + tagElement.text();
                        tagList.add(tagText);
                    }
                    currentArticle.setTags(tagList);

                    // Lấy ngày xuất bản
                    Element dateElement = articleDoc.select("div.right span.thetime span").first();
                    String publishDateStr = dateElement.text();
                    if (!publishDateStr.isEmpty()) {
                        currentArticle.setPublishDate(DateIO.formatCustomDate(publishDateStr));
                    }

                    articleList.add(currentArticle);

                    System.out.println("Processed article: " + currentArticle.getTitle());
            }
                	                
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
        	System.out.println("Crawl https://todaynftnews.com/tags/nft done!!!");
            driver.quit();
        }
    	return articleList;
	}
	
	private static void scrollNearEnd(WebDriver driver) {
	    try {
	        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	        // Scroll đến vị trí gần cuối trang
	        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight - 100);");

	        // Chờ một chút để trang có thể load thêm dữ liệu (có thể điều chỉnh thời gian này)
	        Thread.sleep(1000);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private static void clickLoadMoreButton(WebDriver driver) {
	    try {
	    	// Scroll xuống gần cuối trang
	        scrollNearEnd(driver);

	        List<WebElement> loadMoreButtons = driver.findElements(By.cssSelector("div#load-posts a"));
	        WebElement loadMoreButton = loadMoreButtons.get(0);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", loadMoreButton);
            // Thực hiện click vào nút Load more posts
            loadMoreButton.click();
	        
	        Thread.sleep(1000); // Đợi một khoảng thời gian để trang load thêm dữ liệu
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
