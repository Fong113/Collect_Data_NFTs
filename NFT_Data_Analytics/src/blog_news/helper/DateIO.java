package blog_news.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateIO {
    private static String formatHelper(String inputDate, String inputPattern, String outputPattern) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
            Date date = inputFormat.parse(inputDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String formatDate(String inputDate) {
        return formatHelper(inputDate, "yyyy-MM-dd'T'HH:mm:ssXXX", "dd/MM/yyyy");
    }

    public static String formatCustomDate(String inputDate) {
        return formatHelper(inputDate, "MMM dd, yyyy", "dd/MM/yyyy");
    }
    
 // Format đối tượng Date thành chuỗi theo định dạng "dd/MM/yyyy"
    public static String formatDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    // Phương thức mới để chuyển đổi chuỗi ngày thành đối tượng Date
    public static Date parseCustomDate(String inputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            return dateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

 // Tạo thời điểm bắt đầu của ngày
    public static Date startOfDay(String day) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            return dateFormat.parse(day + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(); // Trả về ngày hiện tại nếu có lỗi parse
        }
    }

    // Tạo thời điểm kết thúc của ngày
    public static Date endOfDay(String day) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            return dateFormat.parse(day + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(); // Trả về ngày hiện tại nếu có lỗi parse
        }
    }

}
