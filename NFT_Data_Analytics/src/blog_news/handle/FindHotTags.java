package blog_news.handle;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blog_news.Article;
import blog_news.helper.DateIO;

public class FindHotTags {
    private static List<Article> articles;

    public FindHotTags(List<Article> articles) {
        FindHotTags.articles = articles;
    }

    // Hot tags của 1 ngày
    public Map<String, Integer> findHotTagsForDay(String day) {
        Map<String, Integer> tagCountMap = new HashMap<>();
        for (Article article : articles) {
            if (isHotForDay(article, day)) {
                List<String> tags = article.getTags();
                for (String tag : tags) {
                	if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT")) {
                        tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
                    }
                }
            }
        }
        return tagCountMap;
    }

    private boolean isHotForDay(Article article, String day) {
        Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
        Date startOfDay = DateIO.startOfDay(day);
        Date endOfDay = DateIO.endOfDay(day);
        // Kiểm tra xem bài viết có được xuất bản trong ngày cần kiểm tra không
        return publishDate.getTime() >= startOfDay.getTime() && publishDate.getTime() < endOfDay.getTime();
    }
    
    // Hot tags của 1 tuần
    public Map<String, Integer> findHotTagsForWeek(String startDate) {
        Map<String, Integer> tagCountMap = new HashMap<>();
        
        // Parse ngày bắt đầu của tuần
        Date startOfWeek = DateIO.startOfDay(startDate);

        // Tính toán ngày kết thúc của tuần
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startOfWeek);
        calendar.add(Calendar.DAY_OF_WEEK, 6); // Số ngày trong một tuần
        Date endOfWeek = DateIO.endOfDay(DateIO.formatDateToString(calendar.getTime()));
        System.out.println("Start: " + startOfWeek); // có thể bỏ
        System.out.println("End: " + endOfWeek); // có thể bỏ
        for (Article article : articles) {
            Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
            // Kiểm tra xem bài viết có được xuất bản trong khoảng thời gian của tuần không
            if (publishDate.getTime() >= startOfWeek.getTime() && publishDate.getTime() <= endOfWeek.getTime()) {
                List<String> tags = article.getTags();
                for (String tag : tags) {
                    // Loại bỏ hai tags "NFT market" và "NFT"
                    if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT")) {
                        tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
                    }
                }
            }
        }
        return tagCountMap;
    }
    
    public Map<String, Integer> findHotTagsForMonth(String month) {
        Map<String, Integer> tagCountMap = new HashMap<>();
        for (Article article : articles) {
            if (isHotForMonth(article, month)) {
                List<String> tags = article.getTags();
                for (String tag : tags) {
                	if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT")) {
                        tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
                    }
                }
            }
        }
        return tagCountMap;
    }

    private boolean isHotForMonth(Article article, String month) {
        Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publishDate);

        int articleMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
        int targetMonth = Integer.parseInt(month);

        // Kiểm tra xem bài viết có được xuất bản trong tháng cần kiểm tra không
        return articleMonth == targetMonth;
    }


}


