package twitter;

import java.util.List;

import twitter.ITwitter;
import twitter.handle.Tweet;
import twitter.handle.Handle;

public class test {
	public static void main(String[] args) {
		ITwitter data = new Handle();
		
		List<Tweet> test = data.getTweetsByTag("btc");

		for (Tweet tweet : test) {
			if(tweet == null) break;
			System.out.println(tweet.getContent());
		}
	}
}