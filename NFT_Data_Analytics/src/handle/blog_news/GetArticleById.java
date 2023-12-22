package handle.blog_news;

import java.util.List;

import model.Article;

public class GetArticleById {
	private static List<Article> articles;
	public GetArticleById(List<Article> articles) {
		GetArticleById.articles = articles;
	}

	public Article findArticleById(int inputId) {
        return articles.stream()
                .filter(article -> article.getId() == inputId)
                .findFirst()
                .orElse(null);
    }

}
