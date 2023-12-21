package twitter.crawl.selenium;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twitter.crawl.ICrawler;
import twitter.helper.exception.InternetConnectionException;
import twitter.helper.format.FormatName;
import twitter.helper.format.LocalDateAdapter;
import twitter.model.Tweet;

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
