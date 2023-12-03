package blog_news.handle;

import java.util.List;

public interface IArticleManager {
	 void filterArticlesByTags();
	 List<?> filterArticlesByTags(String[] targetTags);
}
