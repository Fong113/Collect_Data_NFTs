package test;

import java.util.List;

import dao.twitter.DAOTwitter;
import dao.twitter.IDAOTwitter;
import handle.twitter.AHandle.TimePeriodType;


public class Test {
	public static void main(String[] args) throws InterruptedException {

		IDAOTwitter t = new DAOTwitter();
//		t.refreshData();
		
//		List<Tweet> tweets = t.getTweetsByNameNFTs("luna");
//		
//		for(Tweet test : tweets) {
//			System.out.println(Arrays.toString(test.getTags().stream().toArray()));
//		}
//		
		
		List<String> tags = t.getHotTags(TimePeriodType.DAILY);
		int i = 0;
		for(String test : tags) {
			System.out.println(i + " " + test);
			i++;
		}

		
	}
}
