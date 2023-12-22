package handle.blog_news;

import java.util.List;

import model.Article;

public class GetAllArticles {
	private static List<Article> articles;
	
	public GetAllArticles(List<Article> articles) {
		GetAllArticles.articles = articles;
	}
	
	public List<Article> getAllArticles() {
		return articles;
    }
}
