package dao.blog_news;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

import handle.blog_news.GetAllArticles;
import handle.blog_news.GetAllTags;
import handle.blog_news.GetArticleById;
import handle.blog_news.GetArticlesByTags;
import handle.blog_news.GetHotTags_Blogs;
import handle.blog_news.GetHotTags_Blogs.TimePeriodType;
import helper.JsonIO;
import model.Article;

public class DAOBlog_news implements IDAOBlog_news {
	private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
	private static List<Article> articles;
	public DAOBlog_news() {
		 DAOBlog_news.articles = Article_IO.loadJson(Article.getPATH());
	 }	
	@Override
	public List<Article> getAllArticles() {
		GetAllArticles GetAllArticles = new GetAllArticles(articles);
		return GetAllArticles.getAllArticles();
	}
	
	@Override
	public List<Article> filterArticlesByTags(String[] targetTags) {
		GetArticlesByTags GetArticlesByTags = new GetArticlesByTags(articles);
		return GetArticlesByTags.filterArticlesByTags(targetTags);
	}
	@Override
	public Set<String> getUniqueTags() {
		GetAllTags GetAllTags = new GetAllTags(articles);
		return GetAllTags.getUniqueTags();	
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
