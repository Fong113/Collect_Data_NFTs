package blog_news.handle;

import java.util.List;

import blog_news.Article;

public class ArticleManager implements IArticleManager {
	private static List<Article> articles;
	public ArticleManager(List<Article> articles) {
		ArticleManager.articles = articles;
	}

	@Override
	public List<Article> filterArticlesByTags(String[] targetTags) {
		ShowArticlesByTags showArticlesByTags = new ShowArticlesByTags(articles);
		return showArticlesByTags.filterArticlesByTags(targetTags);
	}

	@Override
	public Article findArticleById(int id) {
		ShowAnArticle showAnArticle = new ShowAnArticle(articles);
		return showAnArticle.findArticleById(id);
	} 
	
}
