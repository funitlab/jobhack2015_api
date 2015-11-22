package widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;


public class TimoButton extends Button{
	public enum fonts {
		opensans_bold, opensans_bolditalic, opensans_extrabold, opensans_extrabolditalic, opensans_italic, opensans_light, opensans_lightitalic, opensans_regular, opensans_semibold, opensans_semibolditalic, opensans, sans_serif_light
	}

	public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

	public TimoButton(Context context) {
		super(context);

		applyCustomFont(context, null);
	}

	public TimoButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		applyCustomFont(context, attrs);
	}

	public TimoButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		applyCustomFont(context, attrs);
	}

	private void applyCustomFont(Context context, AttributeSet attrs) {
		String fontFamily = attrs.getAttributeValue(ANDROID_SCHEMA,
				"fontFamily");

		Typeface customFont = selectTypeface(context, fontFamily);
		setTypeface(customFont);
	}

	private Typeface selectTypeface(Context context, String fontFamily) {
		/*
		 * information about the TextView textStyle:
		 * http://developer.android.com
		 * /reference/android/R.styleable.html#TextView_textStyle
		 */
		if (TextUtils.isEmpty(fontFamily)) {
			return FontCache.getTypeface("fonts/OpenSans-Regular.ttf", context);
		}

		fontFamily = fontFamily.toLowerCase().replace("-", "_");

		switch (fonts.valueOf(fontFamily)) {
		case opensans_bold: // bold
			return FontCache.getTypeface("fonts/OpenSans-Bold.ttf", context);

		case opensans_italic: // italic
			return FontCache.getTypeface("fonts/OpenSans-Italic.ttf", context);

		case opensans_regular: // regular
			return FontCache.getTypeface("fonts/OpenSans-Regular.ttf", context);
		case opensans_bolditalic: // opensans_bolditalic
			return FontCache.getTypeface("fonts/OpenSans-BoldItalic.ttf",
					context);
		case opensans_extrabold: // opensans_extrabold
			return FontCache.getTypeface("fonts/OpenSans-ExtraBold.ttf",
					context);
		case opensans_extrabolditalic: // opensans_extrabolditalic
			return FontCache.getTypeface("fonts/OpenSans-ExtraBoldItalic.ttf",
					context);
		case opensans_light: // opensans_light
			return FontCache.getTypeface("fonts/OpenSans-Light.ttf", context);
		case opensans_lightitalic: // opensans_lightitalic
			return FontCache.getTypeface("fonts/OpenSans-LightItalic.ttf",
					context);
		case opensans_semibold: // opensans_semibold
			return FontCache
					.getTypeface("fonts/OpenSans-Semibold.ttf", context);
		case opensans_semibolditalic: // opensans_semibolditalic
			return FontCache.getTypeface("fonts/OpenSans-SemiboldItalic.ttf",
					context);
		case sans_serif_light: // opensans_semibolditalic
			return FontCache.getTypeface("fonts/OpenSans-Light.ttf", context);
		case opensans: // opensans_semibolditalic
			return FontCache.getTypeface("fonts/OpenSans-Regular.ttf", context);
		default:
			return FontCache.getTypeface("fonts/OpenSans-Regular.ttf", context);

		}
	}
}
