package alertview;

import models.BrowseItem;
import volley.CircleNetworkImageView;
import interfaces.AlertViewObjectInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.JobList;

import common.JobHackImageDrawable;

public class BrowseDialog {
	private static final String TAG = "GoalNameDialog";

	public static void showBrowseDialog(final Context context,
			final String title, final JobList item, ImageLoader imageLoader,
			final BrowseDialogInterface inter) {

		if (((Activity) context).isFinishing()) {
			// Activity killed.
			Log.d(TAG, "Activity was killed! Do not show!");
			return;
		}

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialoglayout = inflater.inflate(R.layout.view_dialog_browse, null);
		alertDialog.setView(dialoglayout);

		alertDialog.create();

		// Showing Alert Message
		final AlertDialog ad = alertDialog.show();

		ad.setCanceledOnTouchOutside(true);

		// Bind Id and Load data
		TextView tvJobName = (TextView) dialoglayout
				.findViewById(R.id.tvJobName);
		TextView tvSalary = (TextView) dialoglayout.findViewById(R.id.tvSalary);
		TextView tvDescription = (TextView) dialoglayout
				.findViewById(R.id.tvDescription);
		TextView tvCompanyName = (TextView) dialoglayout
				.findViewById(R.id.tvCompanyName);
		CircleNetworkImageView imgJobIcon = (CircleNetworkImageView) dialoglayout
				.findViewById(R.id.imgJobIcon);

		 tvJobName.setText(item.getJobTitle());
	        tvCompanyName.setText(item.getJobCompany());
	        
	        if(item.getJobLogoUrl() != null && item.getJobLogoUrl().length() != 0){
	          imgJobIcon.setImageUrl(item.getJobLogoUrl(), imageLoader);

	        }else{
	        	Resources resources = context.getResources();
	        	final int resourceId = resources.getIdentifier("ic_developer", "drawable", 
	        	   context.getPackageName());
	        	Drawable iconDrawable =  resources.getDrawable(resourceId);
	        	
	        	JobHackImageDrawable.setImageDrawble(imgJobIcon, iconDrawable);
	        }
	        
	        // Converting tvSalary into x ago format
	        tvSalary.setText(item.getSalaryMin());
	 
	        if (!TextUtils.isEmpty(item.getJobDescription())) {
	            tvDescription.setText(item.getJobDescription());
	            tvDescription.setVisibility(View.VISIBLE);
	        } else {
	            tvDescription.setVisibility(View.GONE);
	        }

		Button btnApply = (Button) dialoglayout.findViewById(R.id.btnApply);
		Button btnRecommend = (Button) dialoglayout
				.findViewById(R.id.btnRecommend);
		Button btnPin = (Button) dialoglayout.findViewById(R.id.btnPin);
		CircleNetworkImageView imgClose = (CircleNetworkImageView) dialoglayout
				.findViewById(R.id.imgClose);

		// Apply Button Clicked
		btnApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				
				inter.onApply(item);
			}
		});

		// Recommend Button Clicked
		btnRecommend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				
				inter.onRecommend(item);
			}
		});

		// Pin Button Clicked
		btnPin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				
				inter.onPin(item);
			}
		});

		// Close Button Clicked
		imgClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ad.dismiss();
				
				inter.onDismiss();
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
	
	public interface BrowseDialogInterface {
		void onApply(JobList job);
		void onRecommend(JobList job);
		void onPin(JobList job);
		void onDismiss();
	}

}
