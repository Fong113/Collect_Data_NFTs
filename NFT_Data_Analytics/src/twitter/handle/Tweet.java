package twitter.handle;
import java.time.LocalDate;

public class Tweet {

	private int id;
	private String author;
	private String content;
	private String[] tags;
	private String imageURL;
	private LocalDate date;
	 
	private static int nextId = 1;

	public Tweet(String author, String content, String[] tags, String imageURL, LocalDate date) {
		this();
		this.author = author;
		this.content = content;
		this.tags = tags;
		this.imageURL = imageURL;
		this.date = date;
	}
	public Tweet() {
        this.id = nextId;
        nextId++;
    }
	public int getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public String[] getTags() {
		return tags;
	}

	public String getImageURL() {
		return imageURL;
	}

	public LocalDate getDate() {
		return date;
	}

}
