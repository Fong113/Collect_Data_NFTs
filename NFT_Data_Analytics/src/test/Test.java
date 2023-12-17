package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import twitter.ITwitter;
import twitter.handle.HandleTwitter;
import twitter.handle.HandleTwitter.TimePeriodType;
import twitter.handle.Tweet;


public class Test {
	public static void main(String[] args) {

		ITwitter t = new HandleTwitter();
		t.refreshData();
//		
//		List<Tweet> tweets = t.getTweetsAboutNFTs();
//		
//		for(Tweet test : tweets) {
//			System.out.println(Arrays.toString(test.getTags()));
//		}
		
		
		List<String> tags = t.getHotTags(TimePeriodType.DAILY);
		for(String test : tags) {
			System.out.println(test);
		}
	}
}
