package blog_news.handle;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import blog_news.Article;
import blog_news.crawl.Todaynftnews_crawler;
import blog_news.helper.JsonIO;

public class HandleArticleManager implements IArticleManager {
	 private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());

	 private ShowArticlesByTags articleManager;

	 public HandleArticleManager(List<Article> articles) {
	     this.articleManager = new ShowArticlesByTags(articles);
	 }	
	 
	 //Chỉ để test trong console
	public void filterArticlesByTags() {
    	articleManager.filterArticlesByTags();
	}
	// function chính
	@Override
	public List<?> filterArticlesByTags(String[] targetTags) {
		return articleManager.filterArticlesByTags(targetTags);
	}
    
    public static void main(String[] args) {
        List<Article> articles1 = Article_IO.loadJson(Todaynftnews_crawler.getPATH());
//        List<Article> articles2 = Article_IO.loadJson(Cointelegraph_crawler.getPATH());
//        List<Article> combinedArticles = new ArrayList<>(articles1);
//        combinedArticles.addAll(articles2);
        
        HandleArticleManager handleArticleManager = new HandleArticleManager(articles1);

        //Chỉ để test trong console
        handleArticleManager.filterArticlesByTags();
        
//        JsonIO.clearBlogNewsData();
    }
	

}
