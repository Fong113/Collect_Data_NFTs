package blog_news.handle;

import java.util.List;

import blog_news.Article;

public class ShowAllAriticles {
	private static List<Article> articles;
	public ShowAllAriticles(List<Article> articles) {
		ShowAllAriticles.articles = articles;
	}

	public String getAllArticles() {
        StringBuilder result = new StringBuilder();
        for (Article article : articles) {
            result.append(article.toString()).append("\n");
        }
        return result.toString();
    }
}
