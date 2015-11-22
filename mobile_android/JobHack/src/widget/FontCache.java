package widget;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class FontCache {

private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

public static Typeface getTypeface(String fontname, Context context) {
    Typeface typeface = fontCache.get(fontname);

    if (typeface == null) {
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), fontname);
        } catch (Exception e) {
            return null;
        }

        fontCache.put(fontname, typeface);
    }

    return typeface;
}
}