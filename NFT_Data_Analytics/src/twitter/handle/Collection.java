package twitter.handle;

import java.time.LocalDate;

public class Collection {
	
	private int id;
	private String author;
	private String content;
	private String[] tags;
	private String imageURL;
	private String date;
	
	public Collection(int id, String author, String content, String[] tags, String imageURL, String date) {
		super();
		this.id = id;
		this.author = author;
		this.content = content;
		this.tags = tags;
		this.imageURL = imageURL;
		this.date = date;
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

	public String getDate() {
		return date;
	}

	
	

}
