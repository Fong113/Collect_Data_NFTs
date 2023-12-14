package twitter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import twitter.ITwitter;
import twitter.handle.Tweet;
import twitter.handle.Handle;
import twitter.handle.Handle.PeriodType;

public class test {
	public static void main(String[] args) {
		ITwitter data = new Handle();
		
		List<Tweet> test = data.getTweetsNFTs();
		List<Tweet> tweetsInDay = test.stream()
                .filter(tweet -> tweet.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
		
		 LocalDate endOfWeek = LocalDate.now().plusDays(6);
         List<Tweet> tweetsInWeek = test.stream()
                 .filter(tweet -> tweet.getDate().isAfter(LocalDate.now().minusDays(1)) && tweet.getDate().isBefore(endOfWeek.plusDays(1)))
                 .collect(Collectors.toList());
         
         
//         int i = 0;
//         for (Tweet tweet : tweetsInDay) {
//        	 
//			if(tweet == null) break;
//			System.out.println(i + ". " + Arrays.toString(tweet.getTags()));
//			i++;
//		}
		
		List<String> hotTagsDaily = data.getHotTags(PeriodType.WEEKLY);
		for (String string : hotTagsDaily) {
			System.out.println(string);
		}
		
		
//		List<Tweet> searchbytag = data.getTweetsByTag("#Mugshot");
//		for (Tweet tweet : searchbytag) {
//       	 
//			if(tweet == null) break;
//			System.out.println(tweet.getId() + ". " + Arrays.toString(tweet.getTags()));
//			i++;
//		}
	 
	}
}