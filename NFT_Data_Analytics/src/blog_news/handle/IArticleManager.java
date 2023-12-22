package blog_news.handle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import blog_news.Article;
import blog_news.handle.FindHotTags.TimePeriodType;

public interface IArticleManager {
//	 void filterArticlesByTags(); // Chỉ để test trong console
	// function chính
	 List<?> filterArticlesByTags(String[] targetTags); 
	 public List<Article> getAllArticles();
	 public Set<String> extractUniqueTags();
	 public Article findArticleById(int id);
	 public List<String> findHotTagsForDay();
	 public List<String> findHotTagsForWeek();
	 public List<String> findHotTagsForMonth();
	 public List<String> getHotTags(TimePeriodType periodType);
	 
}
