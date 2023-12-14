package twitter;

import twitter.ITwitter;
import twitter.handle.Collection;
import twitter.handle.Handle;

public class test {
	public static void main(String[] args) {
		ITwitter data = new Handle();
//		Collection[] test = data.getTweetNFTs();
//		
//		for(Collection tweet : test) {
//			System.out.println(tweet.getAuthor());
//		}
		
		Collection[] test = data.getTweetByTag("Lil Pudgys");
		
		for(Collection tweet : test) {
			System.out.println(tweet.getContent());
		}
	}
}