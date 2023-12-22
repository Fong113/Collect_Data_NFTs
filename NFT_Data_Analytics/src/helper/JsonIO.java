package helper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonIO<T> {
	private final Type TYPE;   //the type of objects to be deserialized from Json
	FileWriter fileWriter;
	FileReader fileReader;
	
	public JsonIO(Type type) {
		this.TYPE = type;
	}
	
	public void writeToJson(List<T> list, String filePath) {
		try {
			File file = new File(filePath);
		    if (!file.exists()) {
		            if (file.createNewFile()) {
		                System.out.println("File created: " + file.getName());
		            } else {
		                System.out.println("Unable to create file.");
		            }
		    }
		}catch (IOException e) {
	        e.printStackTrace();
	    } finally {
		    try (FileWriter fileWriter = new FileWriter(filePath)) {
		        Gson gson = new GsonBuilder().setPrettyPrinting().create();
		        fileWriter.write(gson.toJson(list));
		        fileWriter.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }
	}
	
	public List<T> loadJson(String path) {
	    List<T> list = null;
	    try {
	        File file = new File(path);
	        if (!file.exists()) {
	            return new ArrayList<>();
	        }

	        fileReader = new FileReader(path);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        list = gson.fromJson(fileReader, TYPE);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (fileReader != null) {
	                fileReader.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return list != null ? list : new ArrayList<>();
	}
	
	public static void clearBlogNewsData(String filePath) {
		try {
	        File file = new File(filePath);
	        if (!file.exists()) {
	            return;
	        }
	        Files.write(Path.of(filePath), Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
}
