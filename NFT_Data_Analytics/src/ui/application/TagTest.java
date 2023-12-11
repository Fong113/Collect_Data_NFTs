package ui.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blog_news.Article;

public class TagTest {
	private static Set<String> tags;
	
	public TagTest() {}
	
	public Set<String> getAllUniqueTags(List<Article> articles) {
		Set<String> uniqueTags = new HashSet<>();
	    for (Article article : articles) {
	        uniqueTags.addAll(article.getTags());
	    }
	    return uniqueTags;
	}
}
