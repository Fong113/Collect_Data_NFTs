package crawler.twitter.selenium;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.exception.InternetConnectionException;
import model.Tweet;

public class ActionOnTwitter {

	private static  WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));

	public static void visitWebsite(String pathToWebsite) throws InternetConnectionException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.navigate().to(pathToWebsite);
	}

	public static void enterUsername(String usename) {
		driver.findElement(By.xpath("//input[@autocomplete='username']")).sendKeys(usename);
		driver.findElement(By.xpath("//input[@autocomplete='username']/following::div[@role='button']")).click();
	}

	public static void enterPassword(String password) {
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//div[@data-testid='LoginForm_Login_Button']")).click();
	}

	public static void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public static void searchByTag(String tag) throws InterruptedException {

		driver.findElement(By.xpath("//a[@data-testid='AppTabBar_Explore_Link']")).click();
		WebElement search = driver.findElement(By.xpath("//input[@data-testid='SearchBox_Search_Input']"));
		search.sendKeys(tag);
		search.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']")));
		
	}

	public static List<Tweet> getArrayTweetList(int tweetsQuantity) throws InterruptedException {
		
		System.out.println("Getting tweet......");

		List<WebElement> posts = new ArrayList<WebElement>();
		
		List<Tweet> tweetList = new ArrayList<Tweet>();
		
		while (posts.size() < tweetsQuantity) {
			
			scrollDown();
			Thread.sleep(1000);
			List<WebElement> newPosts = driver.findElements(By
					.xpath("//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']//time/ancestor::article"));
			
			for (WebElement post : newPosts) {
				if (!posts.contains(post)) {
					posts.add(post);
					Tweet convertTweet = takeTweet(post);
					System.out.println(convertTweet.toString());
					tweetList.add(convertTweet);
				}
			}
		}
		return tweetList;
	}

	public static String getAuthor(WebElement tweet) {
		return tweet.findElement(By.xpath(".//div[@data-testid='User-Name']//a")).getText();
	}

	public static String getContent(WebElement tweet) {
		return tweet.findElement(By.xpath(".//div[@data-testid='tweetText']")).getText();
	}

	public static List<String> getTags(WebElement tweet) {
		List<WebElement> crawlTags = tweet.findElements(By.partialLinkText("#"));
		List<String> tags = crawlTags.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
		if (tags.size() == 0) tags.add("#nfts");
		return tags;
	}

	public static String getImageURL(WebElement tweet) {
		List<WebElement> images = tweet.findElements(By.xpath(".//div[@data-testid='tweetPhoto']//img"));
		if (!images.isEmpty()) return images.get(0).getAttribute("src");
		return "";
	}

	public static LocalDate getTimePost(WebElement tweet) {
		WebElement timePost = tweet.findElement(By.xpath(".//div[@data-testid='User-Name']//time"));
		String dateString = timePost.getAttribute("datetime");
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		LocalDate localDate = LocalDate.parse(dateString, inputFormatter);
		return localDate;
	}


	public static Tweet takeTweet(WebElement tweet) {
		String author = getAuthor(tweet);
		List<String> tags = getTags(tweet);
		String content = getContent(tweet);
		String imageURL = getImageURL(tweet);
		LocalDate date = getTimePost(tweet);

		return (new Tweet(author, content, tags, imageURL, date));
	}

	public static void quitTwitter() {
		driver.quit();
	}
	public String formartNameFile(String nameFile) {
		
		String convertName = nameFile.replace(' ', '_').toLowerCase();
		return convertName;
 }

}
