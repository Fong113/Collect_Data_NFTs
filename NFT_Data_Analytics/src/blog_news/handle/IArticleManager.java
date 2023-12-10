package blog_news.handle;

import java.util.List;
import java.util.Map;

import blog_news.Article;

public interface IArticleManager {
//	 void filterArticlesByTags(); // Chỉ để test trong console
	// function chính
	 List<?> filterArticlesByTags(String[] targetTags); 
	 public Article findArticleById(int id);
	 public Map<String, Integer> findHotTagsForDay(String day);
	 public Map<String, Integer> findHotTagsForWeek(String startDate);
	 public Map<String, Integer> findHotTagsForMonth(String month);
}
