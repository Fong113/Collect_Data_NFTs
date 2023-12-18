package twitter.handle;


import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import twitter.ITwitter;
import twitter.crawl.ICrawler;
import twitter.crawl.selenium.HandleSeleniumCrawl;


public class HandleTwitter extends AHandle implements ITwitter {

    @Override
    public List<Tweet> getTweetsAboutNFTs() {

        List<Tweet> tweets = getTweetsFromJsonFile("nfts");

        return tweets;
    };

    @Override
    public void refreshData() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver-win64\\chromedriver.exe");
        ICrawler takeData = new HandleSeleniumCrawl();
        takeData.crawlTweetsAboutNFTs();
    };

    @Override
    public List<Tweet> getTweetsByNameNFTs(String searchKey) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver-win64\\chromedriver.exe");
        ICrawler takeData = new HandleSeleniumCrawl();
        List<Tweet> tweets = takeData.crawlTweetsByNameNFTs(searchKey);

        return tweets;
    };

    @Override
    public List<Tweet> getTweetsByTag(String tag) {

        List<Tweet> tweets = getTweetsFromJsonFile("nfts");

        List<Tweet> resultTweets = searchTweetsByKey(tweets, tag);

        return resultTweets;
    };
    
    @Override
    public List<String> getHotTags(TimePeriodType periodType) {

        List<Tweet> tweets = getTweetsFromJsonFile("nfts");

        switch (periodType) {
            case DAILY:
                List<Tweet> tweetsInDay = tweets.stream()
                        .filter(tweet -> tweet.getDate().equals(LocalDate.now().minusDays(1)))
                        .collect(Collectors.toList());
                return getMostUsedTags(tweetsInDay);
            case WEEKLY:
                LocalDate endOfWeek = LocalDate.now().minusDays(7);
                List<Tweet> tweetsInWeek = tweets.stream()
                        .filter(tweet -> tweet.getDate().isAfter(endOfWeek.minusDays(1))
                                && tweet.getDate().isBefore(LocalDate.now().plusDays(1)))
                        .collect(Collectors.toList());
                return getMostUsedTags(tweetsInWeek);
            case MONTHLY:
                LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
                LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
                List<Tweet> tweetsInMonth = tweets.stream()
                        .filter(tweet -> tweet.getDate().isAfter(startOfMonth.minusDays(1))
                                && tweet.getDate().isBefore(endOfMonth.plusDays(1)))
                        .collect(Collectors.toList());
                return getMostUsedTags(tweetsInMonth);
        }
        return null;

    }

}
