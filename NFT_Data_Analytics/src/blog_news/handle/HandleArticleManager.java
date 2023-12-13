package blog_news.handle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

import blog_news.Article;
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
	public String getAllArticles() {
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
	public Map<String, Integer> findHotTagsForDay() {
		return ArticleManager.findHotTagsForDay();
	}
    
    @Override
	public Map<String, Integer> findHotTagsForWeek() {
		return ArticleManager.findHotTagsForWeek();
	}
    
    @Override
	public Map<String, Integer> findHotTagsForMonth() {
		return ArticleManager.findHotTagsForMonth();
	}
    
    public void test(HandleArticleManager handleArticleManager) {
    	try (Scanner scanner = new Scanner(System.in)) {
//    	    System.out.print("Enter the article ID: ");
//    	    int inputId = scanner.nextInt();
//
//    	    // Find the article by ID
//    	    Article foundArticle = findArticleById(inputId);
//
//    	    // Display the information or handle the case when the article is not found
//    	    if (foundArticle != null) {
//    	        System.out.println("Article found:");
//    	        System.out.println(foundArticle);
//    	    } else {
//    	        System.out.println("Article with ID " + inputId + " not found.");
//    	    }
//
//    	    // Clear the buffer
//    	    scanner.nextLine();
//
//    	    System.out.print("Nhập tags (cách nhau bởi dấu phẩy cách): ");
//    	    if (scanner.hasNextLine()) {
//    	        String inputTags = scanner.nextLine();
//    	        inputTags = '#' + inputTags.replaceAll("\\s*,\\s*", ",#");
//
//    	        // Chia chuỗi tags thành danh sách các tag
//    	        String[] targetTags = inputTags.split(",\\s*");
//    	        // Lọc danh sách bài viết theo tags nhập từ bàn phím
//    	        List<Article> filteredArticles = filterArticlesByTags(targetTags);
//
//    	        // In danh sách bài viết đã lọc
//    	        System.out.println("Search by tags '" + Arrays.toString(targetTags) + "':");
//    	        for (Article article : filteredArticles) {
//    	            System.out.println(articleCount + ". " + article);
//    	            articleCount++;
//    	        }
//
//    	    } else {
//    	        System.out.println("Không có dữ liệu để đọc.");
//    	    }
    		
//    		System.out.print("Nhập ngày (dd/MM/yyyy): ");
//    		System.out.print("Nhập tháng (MM): ");
//            String userInput = scanner.nextLine();

            // Hiển thị thông tin ngày đã nhập
//            System.out.println("Ngày đã nhập: " + userInput);
//            System.out.println("Ngày đã nhập: " + userInput);
            // Gọi phương thức
//            Map<String, Integer> hotTagsMap = findHotTagsForDay();
//            Map<String, Integer> hotTagsMap = findHotTagsForWeek();
            Map<String, Integer> hotTagsMap = findHotTagsForMonth();
            List<Map.Entry<String, Integer>> hotTagsList = new ArrayList<>(hotTagsMap.entrySet());

            // Sắp xếp List theo giảm dần số lần xuất hiện
            hotTagsList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // In kết quả tags hot theo xếp hạng
//            System.out.println("Top tags hot trong ngày:");
//            System.out.println("Top tags hot trong 7 ngày gần nhất:");
            System.out.println("Top tags hot trong tháng:");
            int count = 0;
            for (Map.Entry<String, Integer> entry : hotTagsList) {
                System.out.println("Top " + (count + 1) + ": " +entry.getKey() + ": " + entry.getValue() + " bài");
                count++;
                if (count == 5) {
                    break; // Đã in ra top 5, thoát khỏi vòng lặp
                }
            }
    	}
    }
    
    public static void main(String[] args) {
//        List<Article> articles = Article_IO.loadJson(Article.getPATH());
//        List<Article> articles2 = Article_IO.loadJson(Cointelegraph_crawler.getPATH());
//        List<Article> combinedArticles = new ArrayList<>(articles1);
//        combinedArticles.addAll(articles2);
        
        HandleArticleManager handleArticleManager = new HandleArticleManager();
        
        handleArticleManager.test(handleArticleManager);
        
        // test getAllAricles
//        String foundArticle = handleArticleManager.getAllArticles();
//        System.out.println(foundArticle);
        
        //test Get Tags
//        Set<String> result = handleArticleManager.extractUniqueTags();
//        System.out.println(result);
    }

}
