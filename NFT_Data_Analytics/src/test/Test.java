package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonSyntaxException;

import marketplace.IMarketplace;
import marketplace.handler.MarketplaceHandler;
import marketplace.model.CollectionFilter;
import twitter.ITwitter;
import twitter.handle.HandleTwitter;
import twitter.handle.AHandle.TimePeriodType;
import twitter.handle.Tweet;

public class Test {
	public static void main(String[] args) throws InterruptedException {

//		ITwitter t = new HandleTwitter();
		// t.refreshData();

		// List<Tweet> tweets = t.getTweetsByNameNFTs("luna");
		//
		// for(Tweet test : tweets) {
		// System.out.println(Arrays.toString(test.getTags().stream().toArray()));
		// }
		//
//
//		List<String> tags = t.getHotTags(TimePeriodType.DAILY);
//		int i = 0;
//		for (String test : tags) {
//			System.out.println(i + " " + test);
//			i++;
//		}
	    IMarketplace m = new MarketplaceHandler();
	    Set<CollectionFilter> collectionList;
	    try {
	    	collectionList = m.filterCollectionListByName("star");
		    for(CollectionFilter c : collectionList) {
			    System.out.println(c.toString());

		    }
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
}
