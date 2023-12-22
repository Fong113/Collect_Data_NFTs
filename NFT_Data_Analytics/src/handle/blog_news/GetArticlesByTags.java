package handle.blog_news;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Article;

public class GetArticlesByTags {
    private static List<Article> articles;

    public GetArticlesByTags(List<Article> articles) {
        GetArticlesByTags.articles = articles;
    }
    
 // function chính
    public List<Article> filterArticlesByTags(String[] targetTags) {
        List<Article> filteredArticles = new ArrayList<>();
        List<String> targetTagsList = Arrays.asList(targetTags);

        // Tạo một map để lưu số lượng bài viết chứa từng tag
        Map<String, Integer> tagCounts = new HashMap<>();

        for (Article article : articles) {
            int matchingTagCount = 0;
            List<String> articleTags = article.getTags();

            // Đếm số lượng tag trùng nhau trong bài viết
            matchingTagCount = (int) targetTagsList.stream()
                    .filter(targetTag -> articleTags.stream()
                            .anyMatch(tag -> tag.toLowerCase().contains(targetTag.toLowerCase())))
                    .count();
            // Nếu các tags đã nhập đều trong 1 bài viết => add
            if (matchingTagCount == targetTags.length) {
                filteredArticles.add(article);
            } else { // Nếu tags đã nhập ở khác bài viết
                // Đếm số lượng bài viết chứa từng tag
                for (String targetTag : targetTagsList) {
                    if (articleTags.stream().anyMatch(tag -> tag.toLowerCase().contains(targetTag.toLowerCase()))) {
                        // Tăng số lượng bài viết chứa tag
                        tagCounts.put(targetTag, tagCounts.getOrDefault(targetTag, 0) + 1);
                    }
                }
            }
        }
        
        // Nếu không có bài viết nào chứa tất cả targetTags, chọn bài viết chứa tag có ít bài viết nhất
        if (filteredArticles.isEmpty()) {
            if (!tagCounts.isEmpty()) {
                String tagWithMinCount = Collections.min(tagCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
                for (Article article : articles) {
                    List<String> articleTags = article.getTags();
                    if (articleTags.stream().anyMatch(tag -> tag.toLowerCase().contains(tagWithMinCount.toLowerCase()))) {
                        filteredArticles.add(article);
                    }
                }
            }
        }

        return filteredArticles;
    }
}
