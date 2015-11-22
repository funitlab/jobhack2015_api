package adapter;

import java.util.List;

import models.BrowseItem;
import volley.CircleNetworkImageView;
import volley.FeedImageView;
import volley.AppController;
import volley.VolleyController;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.ApplyJob;

import common.JobHackImageDrawable;

public class ProfileAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<ApplyJob> feedItems;
	ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

	/** VIEWS **/
	@Bind(R.id.tvJobName)
	TextView tvJobName;
	@Bind(R.id.tvSalary)
	TextView tvSalary;
	@Bind(R.id.tvDescription)
	TextView tvDescription;
	@Bind(R.id.tvCompanyName)
	TextView tvCompanyName;
	@Bind(R.id.imgJobIcon)
	CircleNetworkImageView imgJobIcon;

	@Bind(R.id.tvSent)
	TextView tvSent;
	@Bind(R.id.tvReviewed)
	TextView tvReviewed;
	@Bind(R.id.tvContacted)
	TextView tvContacted;
	@Bind(R.id.tvInterviewed)
	TextView tvInterviewed;
	@Bind(R.id.tvHired)
	TextView tvHired;

	@Bind(R.id.imgSent)
	ImageView imgSent;
	@Bind(R.id.imgReviewed)
	ImageView imgReviewed;
	@Bind(R.id.imgContacted)
	ImageView imgContacted;
	@Bind(R.id.imgInterviewed)
	ImageView imgInterviewed;
	@Bind(R.id.imgHired)
	ImageView imgHired;

	public ProfileAdapter(Context context, List<ApplyJob> feedItems) {
		this.context = context;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.cell_profile, null);

		ButterKnife.bind(this, convertView);

		if (imageLoader == null)
			imageLoader = VolleyController.getInstance().getImageLoader();

		ApplyJob item = feedItems.get(position);

		int status = item.getStatus();
		int failtStep = item.getFailtStep();
		updateStatus(status, failtStep);

		tvJobName.setText(item.getJob().getJobTitle());
		tvCompanyName.setText(item.getJob().getJobCompany());

		
		if (item.getJob().getJobLogoUrl() != null && item.getJob().getJobLogoUrl().length() != 0) {
			imgJobIcon
					.setImageUrl(
							item.getJob().getJobLogoUrl(),
							imageLoader);

		} else {
			Resources resources = context.getResources();
			final int resourceId = resources.getIdentifier("ic_developer",
					"drawable", context.getPackageName());
			Drawable iconDrawable = resources.getDrawable(resourceId);

			JobHackImageDrawable.setImageDrawble(imgJobIcon, iconDrawable);
		}

		// Converting tvSalary into x ago format
		if (item.getType() == ApplyJob.TYPE_APPLY) {
			tvSalary.setText("Applied Recently");
			tvSalary.setTextColor(R.color.cpb_green);
		} else {
			tvSalary.setText("Recommended Nguyen Van Hung");
			tvSalary.setTextColor(R.color.cpb_green_dark);
		}
//		tvSalary.setText(item.getJob().getSalaryMin());

		if (!TextUtils.isEmpty(item.getJob().getJobDescription())) {
			tvDescription.setText(item.getJob().getJobDescription());
			tvDescription.setVisibility(View.VISIBLE);
		} else {
			tvDescription.setVisibility(View.GONE);
		}

		// user profile pic
		// profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		return convertView;
	}

	// Update Browse Status
	private void updateStatus(int status, int failtStep) {
		setDefaultStatus();

		switch (status) {
		case BrowseItem.STATUS_SENT:
			setSentStatus(false);
			break;
		case BrowseItem.STATUS_REVIEWED:
			setSentStatus(false);
			setReviewedStatus(false);
			break;
		case BrowseItem.STATUS_CONTACTED:
			setContactedStatus(false);
			setSentStatus(false);
			setReviewedStatus(false);
			break;
		case BrowseItem.STATUS_INTERVIEWED:
			setInterviewedStatus(false);
			setContactedStatus(false);
			setSentStatus(false);
			setReviewedStatus(false);
			break;
		case BrowseItem.STATUS_HIRED:
			setHiredStatus(false);
			setInterviewedStatus(false);
			setContactedStatus(false);
			setSentStatus(false);
			setReviewedStatus(false);
			break;
		case BrowseItem.STATUS_FAILT:
			setFailtStatus(failtStep);
			break;
		default:
			break;
		}
	}

	private void setSentStatus(boolean isFailt) {

		int color;
		if (isFailt) {
			color = context.getResources().getColor(R.color.fp_failt);
		} else {
			color = context.getResources().getColor(R.color.fp_sent);

		}
		tvSent.setTextColor(color);
		imgSent.setBackgroundColor(color);
	}

	private void setReviewedStatus(boolean isFailt) {

		int color;
		if (isFailt) {
			color = context.getResources().getColor(R.color.fp_failt);
		} else {
			color = context.getResources().getColor(R.color.fp_review);

		}
		tvReviewed.setTextColor(color);
		imgReviewed.setBackgroundColor(color);
	}

	private void setContactedStatus(boolean isFailt) {

		int color;
		if (isFailt) {
			color = context.getResources().getColor(R.color.fp_failt);
		} else {
			color = context.getResources().getColor(R.color.fp_contacted);

		}
		tvContacted.setTextColor(color);
		imgContacted.setBackgroundColor(color);
	}

	private void setInterviewedStatus(boolean isFailt) {

		int color;
		if (isFailt) {
			color = context.getResources().getColor(R.color.fp_failt);
		} else {
			color = context.getResources().getColor(R.color.fp_interviewed);

		}
		tvInterviewed.setTextColor(color);
		imgInterviewed.setBackgroundColor(color);
	}

	private void setHiredStatus(boolean isFailt) {

		int color;
		if (isFailt) {
			color = context.getResources().getColor(R.color.fp_failt);
		} else {
			color = context.getResources().getColor(R.color.fp_hired);

		}
		tvHired.setTextColor(color);
		imgHired.setBackgroundColor(color);
	}

	private void setFailtStatus(int failtStep) {
		setDefaultStatus();

		switch (failtStep) {
		case BrowseItem.STATUS_SENT:
			setSentStatus(true);

			break;
		case BrowseItem.STATUS_REVIEWED:
			setReviewedStatus(true);
			setSentStatus(false);

			break;
		case BrowseItem.STATUS_CONTACTED:
			setContactedStatus(true);
			setReviewedStatus(false);
			setSentStatus(false);

			break;
		case BrowseItem.STATUS_INTERVIEWED:
			setInterviewedStatus(true);
			setContactedStatus(false);
			setReviewedStatus(false);
			setSentStatus(false);
			break;
		case BrowseItem.STATUS_HIRED:
			setHiredStatus(true);
			setContactedStatus(false);
			setReviewedStatus(false);
			setSentStatus(false);
			break;
		default:
			break;
		}
	}

	private void setDefaultStatus() {

		int color = context.getResources().getColor(R.color.fp_uncomplete);

		tvSent.setTextColor(color);
		imgSent.setBackgroundColor(color);

		tvContacted.setTextColor(color);
		imgContacted.setBackgroundColor(color);

		tvReviewed.setTextColor(color);
		imgReviewed.setBackgroundColor(color);

		tvInterviewed.setTextColor(color);
		imgInterviewed.setBackgroundColor(color);

		tvHired.setTextColor(color);
		imgHired.setBackgroundColor(color);
	}

}
