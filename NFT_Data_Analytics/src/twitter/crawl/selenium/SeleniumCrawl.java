package twitter.crawl.selenium;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twitter.handle.Tweet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SeleniumCrawl {

	// protected static WebDriver driver = new ChromeDriver(new
	// ChromeOptions().addArguments("--headless"));
	protected static WebDriver driver = new ChromeDriver();
	protected final int POST_QUANTITY = 100;

	public void visitWebsite(String pathToWebsite) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		// driver.navigate().to(pathToWebsite);
		driver.get(pathToWebsite);
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

		driver.findElement(By.xpath("//a[@data-testid='AppTabBar_Explore_Link']")).click();
		WebElement search = driver.findElement(By.xpath("//input[@data-testid='SearchBox_Search_Input']"));
		search.sendKeys(tag);
		search.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']")));

	}

	public ArrayList<Tweet> getArrayTweetList() {
		System.out.println("Getting tweet......");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<WebElement> posts = driver
				.findElements(By.xpath(
						"//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']//time/ancestor::article"));
		ArrayList<Tweet> tweetList = getArrayTweetList(posts);

		while (posts.size() < POST_QUANTITY) {

			WebElement body = driver.findElement(By.tagName("body"));
			body.sendKeys(Keys.END);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			List<WebElement> newPosts = driver.findElements(By.xpath(
					"//div[@data-testid='cellInnerDiv']//article[@data-testid='tweet']//time/ancestor::article"));

			for (WebElement post : newPosts) {
				if (!posts.contains(post)) {
					posts.add(post);
					tweetList.add(getTweet(post));
				}
			}
		}
		return tweetList;
	}

	public String getAuthor(WebElement tweet) {
		return tweet.findElement(By.xpath(".//div[@data-testid='User-Name']//a")).getText();
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

		if (tags.length == 0) {
			tags = new String[] { "#NFTS" };
		}

		return tags;
	}

	public String getImageURL(WebElement tweet) {
		List<WebElement> images = tweet.findElements(By.xpath(".//div[@data-testid='tweetPhoto']//img"));
		if (!images.isEmpty()) {
			return images.get(0).getAttribute("src");
		} else {
			return "";
		}
	}

	public LocalDate getTimePost(WebElement tweet) {
		WebElement timePost = tweet.findElement(By.xpath(".//div[@data-testid='User-Name']//time"));
		String dateString = timePost.getAttribute("datetime");
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		LocalDate localDate = LocalDate.parse(dateString, inputFormatter);
		return localDate;
	}

	public ArrayList<Tweet> getArrayTweetList(List<WebElement> articleList) {

		ArrayList<Tweet> tweetList = new ArrayList<>();

		for (WebElement tweet : articleList) {
			String author = getAuthor(tweet);
			String[] tags = getTag(tweet);
			String content = getContent(tweet);
			String imageURL = getImageURL(tweet);
			LocalDate date = getTimePost(tweet);
			tweetList.add(new Tweet(author, content, tags, imageURL, date));
		}
		return tweetList;
	}

	public Tweet getTweet(WebElement tweet) {

		String author = getAuthor(tweet);
		String[] tags = getTag(tweet);
		String content = getContent(tweet);
		String imageURL = getImageURL(tweet);
		LocalDate date = getTimePost(tweet);

		return (new Tweet(author, content, tags, imageURL, date));
	}

	public void quitTwitter() {
		driver.quit();
	}

	public void putToFile(String fileName, List<Tweet> tweetList) {
		Gson gson = new GsonBuilder()
				.disableHtmlEscaping()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		String convertFileName = fileName.replace(" ", "_").toLowerCase();
		String pathFile = ".\\data\\twitter\\" + convertFileName + ".json";
		try {
			FileWriter writer = new FileWriter(pathFile);
			gson.toJson(tweetList, writer);
			writer.close();
			System.out.println("Add file " + fileName + " succ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}