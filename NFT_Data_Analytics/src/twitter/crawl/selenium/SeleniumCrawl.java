package twitter.crawl.selenium;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twitter.handle.Collection;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SeleniumCrawl {
	
	protected static WebDriver driver = new ChromeDriver();
	
	public void visitWebsite(String pathToWebsite) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(pathToWebsite);
	}
	
	public void enterUsername(String usename) {
		driver.findElement(By.xpath("//input[@autocomplete='username']")).sendKeys(usename);
        driver.findElement(By.xpath("//input[@autocomplete='username']/following::div[@role='button']")).click();
	}
	
	public void enterPassword(String password) {
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//div[@data-testid='LoginForm_Login_Button']")).click();
	}
	
	public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
	
	public void searchByTag(String tag) {
		
		WebElement search = driver.findElement(By.xpath("//input[@data-testid='SearchBox_Search_Input']"));
        search.sendKeys(tag);
        search.sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']")));
        
	}
	
	public List<WebElement> searchResults(){
        List<WebElement> results = driver.findElements(By.xpath("//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']"));
        return results;
	}
	
	
	public String getAuthor(WebElement tweet) {
		return tweet.findElement(By.xpath(".//div[@data-testid='User-Name']//a[@role='link']")).getText();
	}
	public String getContent(WebElement tweet) {
		 return tweet.findElement(By.xpath(".//div[@data-testid='tweetText']")).getText();
	}
	public String[] getTag(WebElement tweet) {
		
		 List<WebElement> tweet_tag = tweet.findElements(By.partialLinkText("#"));
         List<String> tweetTexts = tweet_tag.stream()
                 .map(WebElement::getText)
                 .collect(Collectors.toList());
         String[] tags = tweetTexts.toArray(new String[0]);
         
         if(tags.length == 0){tags = new String[]{"#NFTS"};}
         
         return tags;
	}
	public String getImageURL(WebElement tweet) {
		List<WebElement> images = tweet.findElements(By.xpath(".//div[@data-testid='tweetPhoto']//img[@draggable='true']"));   
        if (!images.isEmpty()) {
            return images.get(0).getAttribute("src");
        } else {
        	return "";
        }
	}
	public String getTimePost(WebElement tweet) {
		return "aoma";
	}
	
	public void putToFile(String fileName, ArrayList<Collection> tweetList) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		String pathFile = ".\\data\\" + fileName;
		try {
        	FileWriter writer = new FileWriter(pathFile);
        	gson.toJson(tweetList, writer);
			writer.close();
			System.out.println("Add file succ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
