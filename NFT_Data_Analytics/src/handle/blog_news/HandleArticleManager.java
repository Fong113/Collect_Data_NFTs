package handle.blog_news;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

import handle.blog_news.GetHotTags_Blogs.TimePeriodType;
import helper.JsonIO;
import model.Article;

public class HandleArticleManager implements IArticleManager {
	 private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
	 
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
	public Set<String> getUniqueTags() {
		return ArticleManager.getUniqueTags();
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
    
    
    public static void main(String[] args) {       
//        HandleArticleManager handleArticleManager = new HandleArticleManager();

//        System.out.println(handleArticleManager.getHotTags(TimePeriodType.MONTHLY));
        
//        String[] targetTags = {"#FIFA", "Bitcoin", "tag3"};
//        System.out.println(handleArticleManager.filterArticlesByTags(targetTags));    
        
//        Set<String> result = handleArticleManager.getUniqueTags();
//        System.out.println(result);
    }

}
