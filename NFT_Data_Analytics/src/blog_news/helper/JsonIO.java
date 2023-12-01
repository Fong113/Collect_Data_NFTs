package blog_news.helper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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
        try (FileWriter fileWriter = new FileWriter(filePath)) {
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        	fileWriter.write(gson.toJson(list));
			fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
	
	public List<T> loadJson(String path) {
		List<T> list = null;
		try{
			fileReader = new FileReader(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			list = gson.fromJson(fileReader, TYPE);
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
