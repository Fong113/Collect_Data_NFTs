package handle.blog_news;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Article;

public class GetAllTags {
	private static List<Article> articles;

    public GetAllTags(List<Article> articles) {
    	GetAllTags.articles = articles;
    }

    public Set<String> getUniqueTags() {
        Set<String> uniqueTags = new HashSet<>();

        for (Article article : articles) {
            List<String> tags = article.getTags();
            uniqueTags.addAll(tags);
        }

        return uniqueTags;
    }
}


