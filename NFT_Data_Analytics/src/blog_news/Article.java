package blog_news;
import java.util.List;

public class Article {
	private final static String PATH = ".\\data\\blog_news.json";
	private static int idCounter = 1;

	private int id;
    private String title;
    private String absoluteURL;
    private String fullContent;
    private List<String> tags;
    private String publishDate;
    
    public Article() {
        this.id = idCounter++;
    }

    public Article(String title, String absoluteURL, String fullContent, List<String> tags, String publishDate) {
        this.id = idCounter++;
        this.title = title;
        this.absoluteURL = absoluteURL;
        this.fullContent = fullContent;
        this.tags = tags;
        this.publishDate = publishDate;
    }
    
    public static String getPATH() {
		return PATH;
	}
    
    // Getters/Setters
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbsoluteURL() {
        return absoluteURL;
    }

    public void setAbsoluteURL(String absoluteURL) {
        this.absoluteURL = absoluteURL;
    }
	
    public String getFullContent() {
		return fullContent;
	}

	public void setFullContent(String fullContent) {
		this.fullContent = fullContent;
	}

	public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
    	StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("  \"id\": ").append(id).append(",\n");
        jsonBuilder.append("  \"title\": \"").append(title).append("\",\n");
        jsonBuilder.append("  \"absoluteURL\": \"").append(absoluteURL).append("\",\n");
        jsonBuilder.append("  \"fullContent\": \"").append(fullContent).append("\",\n");
        
        // Xử lý danh sách tags
        jsonBuilder.append("  \"tags\": [");
        for (String tag : tags) {
            jsonBuilder.append("\"").append(tag).append("\", ");
        }
        if (!tags.isEmpty()) {
            // Loại bỏ dấu phẩy cuối cùng nếu danh sách tags không rỗng
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }
        jsonBuilder.append("],\n");
        
        jsonBuilder.append("  \"publishDate\": \"").append(publishDate).append("\"\n");
        jsonBuilder.append("}");
        
        return jsonBuilder.toString();
    }

	public static void resetIdCounter(int maxId) {
		idCounter = maxId + 1;	
	}
	
}


