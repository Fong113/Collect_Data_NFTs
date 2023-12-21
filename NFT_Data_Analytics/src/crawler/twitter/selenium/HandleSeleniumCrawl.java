package crawler.twitter.selenium;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import crawler.twitter.ICrawler;
import helper.exception.InternetConnectionException;
import helper.format.FormatName;
import helper.format.LocalDateAdapter;
import model.Tweet;

public class HandleSeleniumCrawl implements ICrawler {


	public void loginTwitter() throws InternetConnectionException {
		ActionOnTwitter.visitWebsite("https://twitter.com/i/flow/login");
		ActionOnTwitter.enterUsername("fongdo113");
		ActionOnTwitter.enterPassword("Phong@2003");
	};

	@Override
	public List<Tweet> crawlTweetsAboutNFTs() throws InternetConnectionException, InterruptedException {
		int  tweetsQuantity = 20;
		loginTwitter();
		ActionOnTwitter.searchByTag("(nft OR nfts) (#nft OR #nfts)");
		
		List<Tweet> tweetList = ActionOnTwitter.getArrayTweetList(tweetsQuantity);
		putToFile("NFTs", tweetList);
		ActionOnTwitter.quitTwitter();

		return tweetList;
	};

	@Override
	public List<Tweet> crawlTweetsByNameNFTs(String nameNFTs) throws InterruptedException, InternetConnectionException {
		int tweetsQuantity = 10;
		loginTwitter();
		ActionOnTwitter.searchByTag(nameNFTs);
		List<Tweet> tweetList = ActionOnTwitter.getArrayTweetList(tweetsQuantity);
		putToFile(nameNFTs, tweetList);
		ActionOnTwitter.quitTwitter();

		return tweetList;
	};
	
	 @Override
	 public void refreshData() throws  InternetConnectionException, InterruptedException {
	        crawlTweetsAboutNFTs();
	    };
	    
	public void putToFile(String fileName, List<Tweet> tweetList) {
		Gson gson = new GsonBuilder()
				.disableHtmlEscaping()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.create();
		String convertFileName = FormatName.formatNameFile(fileName);
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
