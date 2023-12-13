package blog_news.handle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import blog_news.Article;

public class ArticleManager implements IArticleManager {
	private static List<Article> articles;
	public ArticleManager(List<Article> articles) {
		ArticleManager.articles = articles;
	}
	
	@Override
	public String getAllArticles() {
		ShowAllAriticles showAllArticle = new ShowAllAriticles(articles);
		return showAllArticle.getAllArticles();
	}
	@Override
	public Set<String> extractUniqueTags() {
		TagProcessor showAlltags = new TagProcessor(articles);
		return showAlltags.extractUniqueTags();	
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

	@Override
	public Map<String, Integer> findHotTagsForDay() {
		FindHotTags FindHotTags = new FindHotTags(articles);
		return FindHotTags.findHotTagsForDay();
	} 
	
	@Override
	public Map<String, Integer> findHotTagsForWeek(){
		FindHotTags FindHotTags = new FindHotTags(articles);
		return FindHotTags.findHotTagsForWeek();
	}
	
	@Override
	public Map<String, Integer> findHotTagsForMonth(){
		FindHotTags FindHotTags = new FindHotTags(articles);
		return FindHotTags.findHotTagsForMonth();
	}

	
	
}
