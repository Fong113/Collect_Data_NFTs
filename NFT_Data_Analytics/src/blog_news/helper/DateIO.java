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
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String formatDate(String inputDate) {
        return formatHelper(inputDate, "yyyy-MM-dd", "dd/MM/yyyy");
    }

    public static String formatCustomDate(String inputDate) {
        return formatHelper(inputDate, "MMM dd, yyyy", "dd/MM/yyyy");
    }
}
