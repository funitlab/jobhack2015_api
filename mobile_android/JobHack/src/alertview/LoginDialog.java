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

public class LoginDialog {
	private static final String TAG = "GoalNameDialog";

	public static void showLoginDialog(final Context context) {

		if (((Activity) context).isFinishing()) {
			// Activity killed.
			Log.d(TAG, "Activity was killed! Do not show!");
			return;
		}

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialoglayout = inflater.inflate(R.layout.view_dialog_login, null);
		alertDialog.setView(dialoglayout);

		alertDialog.create();

		// Showing Alert Message
		final AlertDialog ad = alertDialog.show();

		ad.setCanceledOnTouchOutside(true);

	}

}
