package alertview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.funitlab.jobhack.R;

public class RatingDialog {
	private static final String TAG = "GoalNameDialog";

	public static void showRatingDialog(final Context context,
			final String title, final String des, ImageLoader imageLoader,
			final RatingDialogInterface inter) {

		if (((Activity) context).isFinishing()) {
			// Activity killed.
			Log.d(TAG, "Activity was killed! Do not show!");
			return;
		}

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialoglayout = inflater.inflate(R.layout.view_dialog_rating, null);
		alertDialog.setView(dialoglayout);

		alertDialog.create();

		// Showing Alert Message
		final AlertDialog ad = alertDialog.show();

		ad.setCanceledOnTouchOutside(true);

		// Bind Id and Load data
		TextView tvTitle = (TextView) dialoglayout.findViewById(R.id.tvTitle);
		TextView tvDes = (TextView) dialoglayout.findViewById(R.id.tvDes);

		tvTitle.setText(title);
		tvDes.setText(des);

		Button btnApply = (Button) dialoglayout.findViewById(R.id.btnApply);
		Button btnRecommend = (Button) dialoglayout
				.findViewById(R.id.btnRecommend);
		Button btnPin = (Button) dialoglayout.findViewById(R.id.btnPin);

		// Apply Button Clicked
		btnApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				inter.onNever();

			}
		});

		// Recommend Button Clicked
		btnRecommend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				inter.onNotNow();

			}
		});

		// Pin Button Clicked
		btnPin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				inter.onOk();
			}
		});

		ad.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				inter.onDismiss();
			}
		});

	}

	public interface RatingDialogInterface {
		void onNever();

		void onOk();

		void onNotNow();

		void onDismiss();
	}

}
