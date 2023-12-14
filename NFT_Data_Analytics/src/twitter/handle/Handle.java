package twitter.handle;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import twitter.ITwitter;

public class Handle implements ITwitter {
	
	@Override
	public Collection[] getTweetNFTs() {
		 Gson gson = new Gson();
		 ArrayList<Collection> dataList = new ArrayList<>();

		 try (FileReader reader = new FileReader(".//data//NFTs.json")) {
			 Type listType = new TypeToken<ArrayList<Collection>>() {}.getType();
	            dataList = gson.fromJson(reader, listType);
		 } catch (IOException e) {
	            e.printStackTrace();
		 		}
		 Collection[] tweets = new Collection[dataList.size()];
		 tweets = dataList.toArray(tweets);
	        
		return tweets;
		
	};
	
	@Override
	public Collection[] getTweetByTag(String collectionName) {
		 Gson gson = new Gson();
		 ArrayList<Collection> dataList = new ArrayList<>();

		 try (FileReader reader = new FileReader(".//data//" + collectionName + ".json")) {
			 Type listType = new TypeToken<ArrayList<Collection>>() {}.getType();
	            dataList = gson.fromJson(reader, listType);
		 } catch (IOException e) {
	            e.printStackTrace();
		 		}
		 Collection[] tweets = new Collection[dataList.size()];
		 tweets = dataList.toArray(tweets);
	        
		return tweets;
		
	};
	
	@Override
	public Collection[] getTrendingTags() {
		return null;
		
	};
}
