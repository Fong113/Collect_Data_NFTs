package handle.blog_news;

import java.util.List;
import java.util.Set;

import handle.blog_news.GetHotTags_Blogs.TimePeriodType;
import model.Article;

public interface IArticleManager {
	 List<Article> getAllArticles();
	 List<Article> filterArticlesByTags(String[] targetTags); 
	 Set<String> getUniqueTags();
	 Article findArticleById(int id);
	 List<String> getHotTags(TimePeriodType periodType);	 
}
