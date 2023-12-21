package twitter.handle;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import twitter.helper.format.FormatName;
import twitter.helper.format.LocalDateAdapter;
import twitter.model.Tweet;

public abstract class AHandle {
		
	 public enum TimePeriodType {
	        DAILY, WEEKLY, MONTHLY
	    }
	 
	 public List<Tweet> getTweetsFromJsonFile(String fileName) {
	        Gson gson = new GsonBuilder()
	                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
	                .create();
	        List<Tweet> dataList = new ArrayList<>();
	        try (FileReader reader = new FileReader(".\\data\\twitter\\" + FormatName.formatNameFile(fileName) + ".json")) {
	            Type listType = new TypeToken<ArrayList<Tweet>>() {
	            }.getType();
	            dataList = gson.fromJson(reader, listType);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return dataList;
	    }

	    public List<Tweet> searchTweetsByKey(List<Tweet> tweets, String key) {

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
	    
	    public List<String> getMostUsedTags(List<Tweet> tweets) {
	    	
	        Map<String, Integer> tagCountMap = new HashMap<>();
	        
	        for (Tweet tweet : tweets) {
	        	
	            for (String tag : tweet.getTags()) {
	                 if (!tag.toLowerCase().startsWith("#nfts")) {
	                 tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
	                 }
	            }
	        }

	        List<Map.Entry<String, Integer>> sortedEntries = tagCountMap.entrySet()
	                .stream()
	                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
	                .collect(Collectors.toList());

	        return sortedEntries.stream()
	                .map(Map.Entry::getKey)
	                .collect(Collectors.toList());
	    }
}
