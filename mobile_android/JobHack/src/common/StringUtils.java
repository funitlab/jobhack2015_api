package common;

import java.util.Locale;

public class StringUtils {

	public StringUtils() {
	}

	public static String getWithLocal(String en, String vi) {
		if (Locale.getDefault().getDisplayLanguage().equals("vi")) {
			return vi;
		} else {
			return en;
		}
	}
	
	public static String removePluralString(String str) {
	    if (str.length() > 0 && str.charAt(str.length()-1)=='s') {
	      str = str.substring(0, str.length()-1);
	    }
	    return str;
	}

	public static String getHtmlString(String text){
		if(text==null)return null;
		return text.replaceAll("\\[b\\]", "<b>").replaceAll("\\[/b\\]", "</b>").replaceAll("\\[/br\\]", "</br>");
		/*text = text.replace("\\[b\\]", "<b>");
		text = text.replace("[/br]", "<br/><strong>");
		text = text.replace("[/b]", "</b></strong>");*/
		//return text;
//		return text.replaceAll("\\[b\\]", "<b>").replaceAll("\\[/b\\]", "</b>").replaceAll("\\[/br\\]", "</br>");
	}
}
