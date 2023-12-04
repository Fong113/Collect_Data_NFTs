package ui;

import twitter.ITwitter;
import twitter.handle.Collection;
import twitter.handle.Handle;

public class Test {
	public static void main(String[] args) {
		ITwitter data = new Handle();
		Collection[] test = data.getTweetNFTs();
		
		for(Collection tweet : test) {
			System.out.println(tweet.getAuthor());
		}
	}
}
