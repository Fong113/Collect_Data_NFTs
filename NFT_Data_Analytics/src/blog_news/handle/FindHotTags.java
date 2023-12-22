package blog_news.handle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import blog_news.Article;
import blog_news.helper.DateIO;

public class FindHotTags {
    private static List<Article> articles;

    public FindHotTags(List<Article> articles) {
        FindHotTags.articles = articles;
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
//    private List<String> getTop5Tags(Map<String, Integer> tagCountMap) {
//        // Sắp xếp Map theo giảm dần của giá trị (số lần xuất hiện)
//        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(tagCountMap.entrySet());
//        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
//
//        // Lấy top 5 tags
//        List<String> top5Tags = new ArrayList<>();
//        int count = 0;
//        for (Map.Entry<String, Integer> entry : entryList) {
//            top5Tags.add(entry.getKey());
//            count++;
//            if (count == 5) {
//                break;
//            }
//        }
//
//        return top5Tags;
//    }
    
    // Hot tags của 1 ngày
//    public List<String> findHotTagsForDay() {
//        String day = findLatestDate();
//        Map<String, Integer> tagCountMap = new HashMap<>();
//
//        for (Article article : articles) {
//            if (isHotForDay(article, day)) {
//                List<String> tags = article.getTags();
//                for (String tag : tags) {
//                    if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT")) {
//                        tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
//                    }
//                }
//            }
//        }
//
//        List<String> hotTags = getTop5Tags(tagCountMap);
//        return hotTags;
//    }
//
//    private boolean isHotForDay(Article article, String day) {
//        Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
//        Date startOfDay = DateIO.startOfDay(day);
//        Date endOfDay = DateIO.endOfDay(day);
//        // Kiểm tra xem bài viết có được xuất bản trong ngày cần kiểm tra không
//        return publishDate.getTime() >= startOfDay.getTime() && publishDate.getTime() < endOfDay.getTime();
//    }
    
 // Hot tags của 1 tuần
//    public List<String> findHotTagsForWeek() {
//    	String day = findLatestDate();
//        Map<String, Integer> tagCountMap = new HashMap<>();
////        System.out.println(day);
//        for (Article article : articles) {
//            if (isHotForWeek(article, day)) {
//                List<String> tags = article.getTags();
//                for (String tag : tags) {
//                    // Loại bỏ hai tags "NFT market" và "NFT"
//                    if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT")) {
//                        tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
//                    }
//                }
//            }
//        }
//        
//        List<String> hotTags = getTop5Tags(tagCountMap);
//        return hotTags;
//    }
//
//    private boolean isHotForWeek(Article article, String day) {
//        Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
//        // Parse ngày gần nhất
//        Date endOfWeek = DateIO.startOfDay(day);
//
//        // Tính toán ngày của 7 ngày trước
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(endOfWeek);
//        calendar.add(Calendar.DAY_OF_WEEK, -7); // Số ngày trong một tuần
//        Date startOfWeek = DateIO.endOfDay(DateIO.formatDateToString(calendar.getTime()));
//        // Kiểm tra xem bài viết có được xuất bản trong khoảng thời gian của tuần không
//        return publishDate.getTime() >= startOfWeek.getTime() && publishDate.getTime() <= endOfWeek.getTime();
//    }
//
//    
//    public List<String> findHotTagsForMonth() {
//    	String day = findLatestDate();
////    	String latestMonth = getLatestMonth();
//        Map<String, Integer> tagCountMap = new HashMap<>();
//        for (Article article : articles) {
//            if (isHotForMonth(article, day)) {
//                List<String> tags = article.getTags();
//                for (String tag : tags) {
//                	if (!tag.equalsIgnoreCase("#NFT market") && !tag.equalsIgnoreCase("#NFT")) {
//                        tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
//                    }
//                }
//            }
//        }
//        List<String> hotTags = getTop5Tags(tagCountMap);
//        return hotTags;
//    }
    
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


//    private boolean isHotForMonth(Article article, String day) {
//    	Date publishDate = DateIO.parseCustomDate(article.getPublishDate());
//    	
//    	
//        // Tính toán ngày bắt đầu và kết thúc của khoảng 30 ngày
//        Date endOfMonth = DateIO.startOfDay(day);
//     
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(endOfMonth);
//        calendar.add(Calendar.MONTH, -1); // Giảm một tháng
//        Date startOfMonth = DateIO.endOfDay(DateIO.formatDateToString(calendar.getTime()));
//        // Kiểm tra xem bài viết có được xuất bản trong khoảng thời gian của 30 ngày từ ngày bắt đầu không
//        return publishDate.getTime() >= startOfMonth.getTime() && publishDate.getTime() <= endOfMonth.getTime();
//    }
}


