package blog_news.handle;

import java.util.List;

import blog_news.Article;

public interface IArticleManager {
//	 void filterArticlesByTags(); // Chỉ để test trong console
	// function chính
	 List<?> filterArticlesByTags(String[] targetTags); 
	 public Article findArticleById(int id);
}
