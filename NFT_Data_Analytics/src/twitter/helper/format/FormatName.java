package twitter.helper.format;

public class FormatName {
	
	public static String formatNameFile(String nameFile) {
		
		String convertName = nameFile.replace(' ', '_').toLowerCase();
		
		return convertName;
	}
}
