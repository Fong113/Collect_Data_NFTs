package twitter.handle;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

import twitter.ITwitter;
import twitter.crawl.ICrawler;
import twitter.crawl.selenium.HandleSeleniumCrawl;
import twitter.crawl.selenium.LocalDateAdapter;

public class Handle implements ITwitter {

    public ArrayList<Tweet> getTweetsFromJsonFile(String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        ArrayList<Tweet> dataList = new ArrayList<>();
        try (FileReader reader = new FileReader(".//data//" + fileName + ".json")) {
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

    @Override
    public List<Tweet> getTweetsNFTs() {

        ArrayList<Tweet> tweets = getTweetsFromJsonFile("NFTs");

        return tweets;
    };

    @Override
    public List<Tweet> getTweetsByNameNFTs(String searchKey) {

        String fileName = searchKey.replace(" ", "_").toLowerCase();
        ArrayList<Tweet> tweets = getTweetsFromJsonFile(fileName);

        return tweets;
    };

    @Override
    public List<Tweet> getTweetsByTag(String tag) {

        List<Tweet> tweets = getTweetsFromJsonFile("NFTs");

        List<Tweet> resultTweets = searchTweetsByKey(tweets, tag);

        if (resultTweets == null) {
            System.out.println("nothing here.....");
            ICrawler takeData = new HandleSeleniumCrawl();
            resultTweets = takeData.getTweetsByNameNFTs(tag);
        }

        return resultTweets;
    };

    public enum PeriodType {
        DAILY, WEEKLY, MONTHLY
    }

    public List<String> getHotTags(PeriodType periodType) {

        List<Tweet> tweets = getTweetsFromJsonFile("NFTs");

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
                if (tag.toLowerCase().startsWith("#nft"))
                    break;
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

    public static List<String> getHotNfts(String jsonFolderPath, PeriodType periodType) {
        Map<String, Long> nftTweetCountMap = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            File folder = new File(jsonFolderPath);
            File[] files = folder.listFiles();

            if (files != null) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        try (JsonReader jsonReader = new JsonReader(new FileReader(file))) {
                            JsonObject nftJson = gson.fromJson(jsonReader, JsonObject.class);

                            JsonArray tweetsArray = nftJson.getAsJsonArray("tweets");
                            for (int i = 0; i < tweetsArray.size(); i++) {
                                JsonObject tweetObject = tweetsArray.get(i).getAsJsonObject();
                                LocalDate tweetDate = tweetObject.get("date") == null ? null
                                        : gson.fromJson(tweetObject.get("date"), LocalDate.class);

                                switch (periodType) {
                                    case DAILY:
                                        nftTweetCountMap.merge(formatter.format(tweetDate), 1L, Long::sum);
                                        break;
                                    case WEEKLY:
                                        int weekNumber = tweetDate != null
                                                ? tweetDate.get(WeekFields.ISO.weekOfWeekBasedYear())
                                                : -1;
                                        nftTweetCountMap.merge(
                                                String.format("W%02d-%d", weekNumber, tweetDate.getYear()), 1L,
                                                Long::sum);
                                        break;
                                    case MONTHLY:
                                        nftTweetCountMap.merge(String.format("%d-%02d", tweetDate.getYear(),
                                                tweetDate.getMonthValue()), 1L, Long::sum);
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Long>> sortedNfts = nftTweetCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());

        return sortedNfts.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

}
