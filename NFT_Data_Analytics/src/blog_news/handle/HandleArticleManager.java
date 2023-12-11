package blog_news.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.reflect.TypeToken;

import blog_news.Article;
import blog_news.crawl.Todaynftnews_crawler;
import blog_news.helper.JsonIO;

public class HandleArticleManager implements IArticleManager {
	 private static final JsonIO<Article> Article_IO = new JsonIO<>(new TypeToken<ArrayList<Article>>() {}.getType());
	 private static int articleCount = 1;
	 
	 private ArticleManager ArticleManager;
	 public HandleArticleManager(List<Article> articles) {
	     this.ArticleManager = new ArticleManager(articles);
	 }	

	// function chính
	@Override
	public List<Article> filterArticlesByTags(String[] targetTags) {
		return ArticleManager.filterArticlesByTags(targetTags);
	}
	
	@Override
    public Article findArticleById(int id) {
    	return ArticleManager.findArticleById(id);
    };
    
    public void test(HandleArticleManager handleArticleManager) {
    	try (Scanner scanner = new Scanner(System.in)) {
    	    System.out.print("Enter the article ID: ");
    	    int inputId = scanner.nextInt();

    	    // Find the article by ID
    	    Article foundArticle = findArticleById(inputId);

    	    // Display the information or handle the case when the article is not found
    	    if (foundArticle != null) {
    	        System.out.println("Article found:");
    	        System.out.println(foundArticle);
    	    } else {
    	        System.out.println("Article with ID " + inputId + " not found.");
    	    }

    	    // Clear the buffer
    	    scanner.nextLine();

    	    System.out.print("Nhập tags (cách nhau bởi dấu phẩy cách): ");
    	    if (scanner.hasNextLine()) {
    	        String inputTags = scanner.nextLine();
    	        inputTags = '#' + inputTags.replaceAll("\\s*,\\s*", ",#");

    	        // Chia chuỗi tags thành danh sách các tag
    	        String[] targetTags = inputTags.split(",\\s*");
    	        // Lọc danh sách bài viết theo tags nhập từ bàn phím
    	        List<Article> filteredArticles = filterArticlesByTags(targetTags);

    	        // In danh sách bài viết đã lọc
    	        System.out.println("Search by tags '" + Arrays.toString(targetTags) + "':");
    	        for (Article article : filteredArticles) {
    	            System.out.println(articleCount + ". " + article);
    	            articleCount++;
    	        }

    	    } else {
    	        System.out.println("Không có dữ liệu để đọc.");
    	    }
    	}
    }
    
    public static void main(String[] args) {
        List<Article> articles = Article_IO.loadJson(Todaynftnews_crawler.getPATH());
//        List<Article> articles2 = Article_IO.loadJson(Cointelegraph_crawler.getPATH());
//        List<Article> combinedArticles = new ArrayList<>(articles1);
//        combinedArticles.addAll(articles2);
        
        HandleArticleManager handleArticleManager = new HandleArticleManager(articles);
        handleArticleManager.test(handleArticleManager);

    }
	

}
