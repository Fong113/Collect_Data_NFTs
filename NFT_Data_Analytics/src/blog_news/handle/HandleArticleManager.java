package blog_news.handle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

import blog_news.Article;
import blog_news.handle.FindHotTags.TimePeriodType;
import blog_news.helper.JsonIO;

public class HandleArticleManager implements IArticleManager {
	 private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
	 private static int articleCount = 1;
	 
	 private ArticleManager ArticleManager;
	 public HandleArticleManager() {
		 List<Article> articles = Article_IO.loadJson(Article.getPATH());
	     this.ArticleManager = new ArticleManager(articles);
	 }	
	
	// function chính
	@Override
	public List<Article> getAllArticles() {
		return ArticleManager.getAllArticles();
	}
	
	@Override
	public Set<String> extractUniqueTags() {
		return ArticleManager.extractUniqueTags();
	}
	
	@Override
	public List<Article> filterArticlesByTags(String[] targetTags) {
		return ArticleManager.filterArticlesByTags(targetTags);
	}
	
	@Override
    public Article findArticleById(int id) {
    	return ArticleManager.findArticleById(id);
    };
    
    @Override
   	public List<String> getHotTags(TimePeriodType periodType) {
   		return ArticleManager.getHotTags(periodType);
   	}
    
//    @Override
//	public List<String> findHotTagsForDay() {
//		return ArticleManager.findHotTagsForDay();
//	}
//    
//    @Override
//	public List<String> findHotTagsForWeek() {
//		return ArticleManager.findHotTagsForWeek();
//	}
//    
//    @Override
//	public List<String> findHotTagsForMonth() {
//		return ArticleManager.findHotTagsForMonth();
//	}
    
    public static void main(String[] args) {
//        List<Article> articles = Article_IO.loadJson(Article.getPATH());
//        List<Article> articles2 = Article_IO.loadJson(Cointelegraph_crawler.getPATH());
//        List<Article> combinedArticles = new ArrayList<>(articles1);
//        combinedArticles.addAll(articles2);
        
        HandleArticleManager handleArticleManager = new HandleArticleManager();
        
//        handleArticleManager.test(handleArticleManager);
//        System.out.println(handleArticleManager.findHotTagsForDay());
        System.out.println(handleArticleManager.getHotTags(TimePeriodType.DAILY));
        // test getAllAricles
//        System.out.println(handleArticleManager.getAllArticles());
        
//        //test Get Tags
//        Set<String> result = handleArticleManager.extractUniqueTags();
//        System.out.println(result);
    }

}
