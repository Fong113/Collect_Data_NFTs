package twitter.handle;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;

import twitter.ITwitter;
import twitter.crawl.ICrawler;
import twitter.crawl.selenium.HandleSeleniumCrawl;
import twitter.crawl.selenium.LocalDateAdapter;

public class Handle implements ITwitter {
	
	public ArrayList<Tweet> getTweetsFromJsonFile(String fileName){
		Gson gson = new GsonBuilder()
		        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		        .create();
		
		ArrayList<Tweet> dataList = new ArrayList<>();
		try (FileReader reader = new FileReader(".//data//"+ fileName+".json")) {
			Type listType = new TypeToken<ArrayList<Tweet>>() {
			}.getType();
			dataList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	 private static List<Tweet> searchTweetsByKey(List<Tweet> tweets, String key) {
		 
	        List<Tweet> result = new ArrayList<>();

	        for (Tweet tweet : tweets) {
	        	if(tweet == null) break;
	            boolean foundInTags = false;

	            if (tweet.getTags() != null) {
	                for (String tag : tweet.getTags()) {
	                    if (tag != null && tag.toLowerCase().contains(key.toLowerCase())) {
	                        foundInTags = true;
	                        break;
	                    }
	                }
	            }
	            if (foundInTags) {
	                result.add(tweet);
	            } else {
	                if (tweet.getContent() != null && tweet.getContent().toLowerCase().contains(key.toLowerCase())) {
	                    result.add(tweet);
	                }
	            }
	        }
	        return result;
	    }
	
	
	@Override
	public List<Tweet> getTweetsNFTs() {
		
		ArrayList<Tweet> tweets = getTweetsFromJsonFile("NFTs");

		return tweets;
	};

	@Override
	public List<Tweet> getTweetsByNameNFTs(String searchKey) {
		
		String fileName = searchKey.replace(" ", "_").toLowerCase();
		ArrayList<Tweet> tweets = getTweetsFromJsonFile(fileName);
		
		return tweets;
	};
	
	@Override
	public List<Tweet> getTweetsByTag(String tag) {
		
		List<Tweet> tweets = getTweetsFromJsonFile("NFTs");
		
		List<Tweet> resultTweets = searchTweetsByKey(tweets, tag);
		
		if(resultTweets == null) {
			System.out.println("nothing here.....");
			ICrawler takeData = new HandleSeleniumCrawl();
			resultTweets = takeData.getTweetsByNameNFTs(tag);
		}

		return resultTweets;
	};
	
	@Override
	public List<String> getTrending() {
		return null;

	};
}
