package blog_news.handle;

import java.util.List;
import blog_news.Article;

public class ShowAnArticle {
	private static List<Article> articles;
	public ShowAnArticle(List<Article> articles) {
		ShowAnArticle.articles = articles;
	}

	public Article findArticleById(int inputId) {
        return articles.stream()
                .filter(article -> article.getId() == inputId)
                .findFirst()
                .orElse(null);
    }

}
