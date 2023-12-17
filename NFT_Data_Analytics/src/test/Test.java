package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import twitter.ITwitter;
import twitter.handle.HandleTwitter;
import twitter.handle.AHandle.TimePeriodType;
import twitter.handle.Tweet;


public class Test {
	public static void main(String[] args) throws InterruptedException {

		ITwitter t = new HandleTwitter();
		t.refreshData();
		
//		List<Tweet> tweets = t.getTweetsByNameNFTs("luna");
//		
//		for(Tweet test : tweets) {
//			System.out.println(Arrays.toString(test.getTags().stream().toArray()));
//		}
//		
		
//		List<String> tags = t.getHotTags(TimePeriodType.DAILY);
//		int i = 0;
//		for(String test : tags) {
//			System.out.println(i + " " + test);
//			i++;
//		}

		
	}
}
