package blog_news.helper;

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
	private final Type TYPE; // the type of objects to be deserialized from Json
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
			// Xử lý exception khi gặp lỗi
			e.printStackTrace();
		}
	}

	public void writeToJsonWithInitialization(List<T> list, String filePath) {
		// Kiểm tra xem file đã tồn tại chưa
		File file = new File(filePath);

		// Nếu file chưa tồn tại, hãy tạo mới
		if (!file.exists()) {
			try {
				if (file.createNewFile()) {
					System.out.println("File created: " + file.getName());
				} else {
					System.out.println("Unable to create file.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Gọi hàm writeToJson để ghi dữ liệu vào file, không quan tâm file có tồn tại
		// hay không
		writeToJson(list, filePath);
	}

	public List<T> loadJson(String path) {
		List<T> list = null;
		try {
			// Kiểm tra xem file có tồn tại không
			File file = new File(path);
			if (!file.exists()) {
				// Nếu file không tồn tại, trả về danh sách rỗng
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
			// Kiểm tra xem file có tồn tại không
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println("FILE NOT FOUND");
				return;
			}

			// Mở file và ghi một danh sách rỗng để xóa nội dung
			Files.write(Path.of(filePath), Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
			// System.out.println("Dữ liệu trong blog_news.json đã được xóa.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
