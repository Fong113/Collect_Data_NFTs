package twitter.handle;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;

import twitter.ITwitter;
import twitter.crawl.ICrawler;
import twitter.crawl.selenium.HandleSeleniumCrawl;
import twitter.crawl.selenium.LocalDateAdapter;

public class HandleTwitter implements ITwitter {

    public enum TimePeriodType {
        DAILY, WEEKLY, MONTHLY
    }

    @Override
    public List<Tweet> getTweetsAboutNFTs() {

        List<Tweet> tweets = getTweetsFromJsonFile("nfts");

        return tweets;
    };

    @Override
    public void refreshData() {
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver-win64\\chromedriver.exe");
        ICrawler takeData = new HandleSeleniumCrawl();
        takeData.loginTwitter();
        takeData.crawlTweetsAboutNFTs();
    };

    @Override
    public List<Tweet> getTweetsByNameNFTs(String searchKey) {
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

    public List<String> getHotTags(TimePeriodType periodType) {

        List<Tweet> tweets = getTweetsFromJsonFile("nfts");

        switch (periodType) {
            case DAILY:
                List<Tweet> tweetsInDay = tweets.stream()
                        .filter(tweet -> tweet.getDate().equals(LocalDate.now()))
                        .collect(Collectors.toList());
                return getMostUsedTags(tweetsInDay);
            case WEEKLY:
                LocalDate endOfWeek = LocalDate.now().plusDays(6);
                List<Tweet> tweetsInWeek = tweets.stream()
                        .filter(tweet -> tweet.getDate().isAfter(LocalDate.now().minusDays(1))
                                && tweet.getDate().isBefore(endOfWeek.plusDays(1)))
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

    private List<String> getMostUsedTags(List<Tweet> tweets) {
        Map<String, Integer> tagCountMap = new HashMap<>();
        for (Tweet tweet : tweets) {
            for (String tag : tweet.getTags()) {
                // if (!tag.toLowerCase().startsWith("#nft")) {
                // tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
                // }
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = tagCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        // Lấy danh sách tag từ map đã sắp xếp
        return sortedEntries.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Tweet> getTweetsFromJsonFile(String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        List<Tweet> dataList = new ArrayList<>();
        try (FileReader reader = new FileReader(".\\data\\twitter\\" + fileName + ".json")) {
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
            if (tweet == null)
                break;
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

}
