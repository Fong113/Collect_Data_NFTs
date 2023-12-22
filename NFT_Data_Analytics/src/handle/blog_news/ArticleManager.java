package handle.blog_news;

import java.util.List;
import java.util.Set;

import handle.blog_news.GetHotTags_Blogs.TimePeriodType;
import model.Article;

public class ArticleManager implements IArticleManager {
	private static List<Article> articles;
	public ArticleManager(List<Article> articles) {
		ArticleManager.articles = articles;
	}
	
	@Override
	public List<Article> getAllArticles() {
		GetAllArticles GetAllArticles = new GetAllArticles(articles);
		return GetAllArticles.getAllArticles();
	}
	@Override
	public Set<String> getUniqueTags() {
		GetAllTags GetAllTags = new GetAllTags(articles);
		return GetAllTags.getUniqueTags();	
	}
	@Override
	public List<Article> filterArticlesByTags(String[] targetTags) {
		GetArticlesByTags GetArticlesByTags = new GetArticlesByTags(articles);
		return GetArticlesByTags.filterArticlesByTags(targetTags);
	}

	@Override
	public Article findArticleById(int id) {
		GetArticleById GetArticleById = new GetArticleById(articles);
		return GetArticleById.findArticleById(id);
	}
	
	@Override
	public List<String> getHotTags(TimePeriodType periodType) {
		GetHotTags_Blogs GetHotTags_Blogs = new GetHotTags_Blogs(articles);
		return GetHotTags_Blogs.getHotTags(periodType);
	}
		
}
