package handle.blog_news;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Article;

public class GetHotTags_Blogs {
    private static List<Article> articles;

    public GetHotTags_Blogs(List<Article> articles) {
        GetHotTags_Blogs.articles = articles;
    }
    
    public enum TimePeriodType {
        DAILY, WEEKLY, MONTHLY
    }
    
    public String findLatestDate() {
        if (articles.isEmpty()) {
            return null;
        }
        String latestDateStr = null;
        LocalDate latestDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Tìm ngày gần nhất
        for (Article article : articles) {
            String articleDateStr = article.getPublishDate();
            LocalDate articleLocalDate = LocalDate.parse(articleDateStr, formatter);

            if (latestDate == null || articleLocalDate.isAfter(latestDate)) {
                latestDate = articleLocalDate;
                latestDateStr = articleDateStr;
            }
        }

        return latestDateStr;
    }  
      
    public List<String> getMostUsedTags(List<Article> searchedArticles) {
    	
        Map<String, Integer> tagCountMap = new HashMap<>();
        
        for (Article article : searchedArticles) {
        	
            for (String tag : article.getTags()) {
            	if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT") && !tag.equalsIgnoreCase("#Blockchain")) {
                    tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = tagCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(5)
                .collect(Collectors.toList());

        return sortedEntries.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getHotTags(TimePeriodType periodType) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        switch (periodType) {
            case DAILY:
                List<Article> blogsInDays = articles.stream()
                        .filter(article -> LocalDate.parse(article.getPublishDate(), formatter).isEqual(now.minusDays(2)))
                        .collect(Collectors.toList());
                return getMostUsedTags(blogsInDays);
            case WEEKLY:
                LocalDate endOfWeek = now.minusDays(7);
                List<Article> blogsInWeek = articles.stream()
                        .filter(article -> {
                            LocalDate publishDate = LocalDate.parse(article.getPublishDate(), formatter);
                            return publishDate.isAfter(endOfWeek.minusDays(1)) && publishDate.isBefore(now.plusDays(1));
                        })
                        .collect(Collectors.toList());
                return getMostUsedTags(blogsInWeek);
            case MONTHLY:
                LocalDate startOfMonth = now.withDayOfMonth(1);
                LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
                List<Article> blogsInMonth = articles.stream()
                        .filter(article -> {
                            LocalDate publishDate = LocalDate.parse(article.getPublishDate(), formatter);
                            return publishDate.isAfter(startOfMonth.minusDays(1)) && publishDate.isBefore(endOfMonth.plusDays(1));
                        })
                        .collect(Collectors.toList());
                return getMostUsedTags(blogsInMonth);
        }
        return null;
    }
}


