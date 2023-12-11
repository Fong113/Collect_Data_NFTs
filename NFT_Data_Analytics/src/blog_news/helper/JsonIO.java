package blog_news.helper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
	
	public static void clearBlogNewsData() {
        String filePath = "E:\\Java\\BTL_OOP\\BTL.OOP.GROUP24\\NFT_Data_Analytics\\data\\blog_news.json";

        try {
            // Mở file và ghi một danh sách rỗng để xóa nội dung
            Files.write(Path.of(filePath), Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
//            System.out.println("Dữ liệu trong blog_news.json đã được xóa.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
