package blog_news.handle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blog_news.Article;

public class TagProcessor {
	private static List<Article> articles;

    public TagProcessor(List<Article> articles) {
    	TagProcessor.articles = articles;
    }

    public Set<String> extractUniqueTags() {
        // Set để lưu trữ tags duy nhất
        Set<String> uniqueTags = new HashSet<>();

        // Duyệt qua từng bài viết
        for (Article article : articles) {
            List<String> tags = article.getTags();

            // Thêm tất cả các tags vào Set
            uniqueTags.addAll(tags);
        }

        // Trả về danh sách tags duy nhất
        return uniqueTags;
    }
}


