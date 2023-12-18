package blog_news.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Duration;

import com.google.gson.reflect.TypeToken;

import blog_news.Article;
import blog_news.helper.DateIO;
import blog_news.helper.JsonIO;

import java.util.ArrayList;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Todaynftnews_crawler implements ICrawler {
	private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
//	private final static String PATH = ".\\data\\blog_news.json";
	private String baseUrl = "https://www.todaynftnews.com/nft-news/";
	
	@Override
	public void crawl() throws IOException, TimeoutException, RuntimeException {
	    List<Article> existingArticles = Article_IO.loadJson(Article.getPATH());

	    if (existingArticles == null || existingArticles.isEmpty()) {
	        // Nếu danh sách là null hoặc trống, khởi tạo danh sách mới
	        existingArticles = new ArrayList<>();
	    } else {
            // Reset idCounter based on the maximum ID in existing articles
            int maxId = existingArticles.stream().mapToInt(Article::getId).max().orElse(0);
            Article.resetIdCounter(maxId);
        }

	    List<Article> crawledArticles = crawlTodayNFTnews(existingArticles);
	    Article_IO.writeToJson(crawledArticles, Article.getPATH());
	}

	
	private List<Article> crawlTodayNFTnews(List<Article> existingArticles) throws IOException, TimeoutException, RuntimeException{
		WebDriverManager.chromedriver().setup();
//    	ChromeOptions opt = new ChromeOptions();
//		opt.setPageLoadStrategy(PageLoadStrategy.EAGER);
//		opt.addArguments("--headless");
//		opt.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
    	WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
//        List<Article> articleList = new ArrayList<>();
        
    	try {
    		    for (int morePage = 2; morePage <= 3; morePage++) {
    		    	clickLoadMoreButton(driver);
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
        	System.out.println("Crawl https://todaynftnews.com/tags/nft done!!!");
            driver.quit();
        }
    	return existingArticles;
	}
	
	private static void scrollNearEnd(WebDriver driver) {
	    try {
	        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	        // Scroll đến vị trí gần cuối trang
	        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight-1000);");
	        Thread.sleep(2000); // Đợi 2 giây để trang load thêm dữ liệu
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private static void clickLoadMoreButton(WebDriver driver) throws RuntimeException {
	    try {
	        // Đóng popup nếu có
	        closePopup(driver);

	        // Scroll xuống gần cuối trang
	        scrollNearEnd(driver);

	        List<WebElement> loadMoreButtons = driver.findElements(By.cssSelector("div#load-posts a"));
	        if (!loadMoreButtons.isEmpty()) {
	            WebElement loadMoreButton = loadMoreButtons.get(0);
	            // Thực hiện click vào nút Load more posts
	            loadMoreButton.click();

	            waitForPageLoad(driver); // Đợi một khoảng thời gian để trang load thêm dữ liệu
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Lỗi xảy ra khi nhấn nút Load more posts.", e);
	    }
	}

	private static void closePopup(WebDriver driver) {
	    try {
	        WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(6)); // Thời gian chờ cho popup
	        popupWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pum-close.popmake-close")));
	        WebElement closeButton = driver.findElement(By.cssSelector(".pum-close.popmake-close"));
	        closeButton.click();
	        System.out.println("Popup đã được đóng.");
	        Thread.sleep(1000);
	    } catch (Exception e) {
	        System.out.println("Popup không xuất hiện hoặc đã hết thời gian chờ.");
	    }
	}

	private static void waitForPageLoad(WebDriver driver) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

	    // Sử dụng expected condition để kiểm tra trạng thái của trang
	    wait.until((ExpectedCondition<Boolean>) wd ->
	            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}

}
