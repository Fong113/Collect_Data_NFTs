package blog_news.handle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public Map<String, Integer> findHotTagsForDay() {
    	String day = findLatestDate();
        Map<String, Integer> tagCountMap = new HashMap<>();
//        System.out.println(day);
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
    public Map<String, Integer> findHotTagsForWeek() {
    	String day = findLatestDate();
        Map<String, Integer> tagCountMap = new HashMap<>();
//        System.out.println(day);
        for (Article article : articles) {
            if (isHotForWeek(article, day)) {
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

    private boolean isHotForWeek(Article article, String day) {
        Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
        // Parse ngày gần nhất
        Date endOfWeek = DateIO.startOfDay(day);

        // Tính toán ngày của 7 ngày trước
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endOfWeek);
        calendar.add(Calendar.DAY_OF_WEEK, -7); // Số ngày trong một tuần
        Date startOfWeek = DateIO.endOfDay(DateIO.formatDateToString(calendar.getTime()));
        // Kiểm tra xem bài viết có được xuất bản trong khoảng thời gian của tuần không
        return publishDate.getTime() >= startOfWeek.getTime() && publishDate.getTime() <= endOfWeek.getTime();
    }

    
    public Map<String, Integer> findHotTagsForMonth() {
    	String day = findLatestDate();
//    	String latestMonth = getLatestMonth();
        Map<String, Integer> tagCountMap = new HashMap<>();
        for (Article article : articles) {
            if (isHotForMonth(article, day)) {
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
    
//    private String getLatestMonth() {
//        String latestMonth = null;
//        for (Article article : articles) {
//            Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(publishDate);
//            int articleMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
//
//            // Chuyển đổi số tháng thành chuỗi
//            String articleMonthString = String.valueOf(articleMonth);
//
//            if (latestMonth == null || articleMonth > Integer.parseInt(latestMonth)) {
//                latestMonth = articleMonthString;
//            }
//        }
//        return latestMonth;
//    }


    private boolean isHotForMonth(Article article, String day) {
    	Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
    	
    	
        // Tính toán ngày bắt đầu và kết thúc của khoảng 30 ngày
        Date endOfMonth = DateIO.startOfDay(day);
     
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endOfMonth);
        calendar.add(Calendar.MONTH, -1); // Giảm một tháng
        Date startOfMonth = DateIO.endOfDay(DateIO.formatDateToString(calendar.getTime()));
        // Kiểm tra xem bài viết có được xuất bản trong khoảng thời gian của 30 ngày từ ngày bắt đầu không
        return publishDate.getTime() >= startOfMonth.getTime() && publishDate.getTime() <= endOfMonth.getTime();
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
}


