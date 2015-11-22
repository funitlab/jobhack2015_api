package common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utils {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter mListAdapter = listView.getAdapter();
		if (mListAdapter == null) {
			// when adapter is null
			return;
		}
		int height = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.UNSPECIFIED);
		for (int i = 0; i < mListAdapter.getCount(); i++) {
			View listItem = mListAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			height += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = height
				+ (listView.getDividerHeight() * (mListAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	public static void hideKeyboard(Context context) {
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (((Activity) context).getCurrentFocus() != null) {
			inputManager.hideSoftInputFromWindow(((Activity) context)
					.getCurrentFocus().getWindowToken(), 0);
		}
	}

	// Show keyboard
	public static void showKeyboard(final Context context, final EditText edt) {
		edt.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager keyboard = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				keyboard.showSoftInput(edt, 0);
			}
		}, 200);
	}
}
