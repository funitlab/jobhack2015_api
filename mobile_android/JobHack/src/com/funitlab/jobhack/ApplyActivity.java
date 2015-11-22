package com.funitlab.jobhack;

import models.BrowseItem;
import services.NotificationService;
import volley.NotificationReponseListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.volley.VolleyError;
import com.funitlab.jobhack.models.ApplyJob;
import com.funitlab.jobhack.models.JobList;
import com.funitlab.jobhack.network.ApplyManager;

public class ApplyActivity extends FragmentActivity {

	public static final String EXTRA_JOB = "job";
	public static final String EXTRA_TYPE = "type";

	@Bind(R.id.edtLink)
	protected EditText edtLink;

	@Bind(R.id.edtCoverLetter)
	protected EditText edtCoverLetter;
	
	@Bind(R.id.tvApplyTitle)
	protected TextView tvApplyTitle;
	
	@Bind(R.id.tvJobTitle)
	protected TextView tvJobTitle;

	private JobList mJob;
	private int mType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mJob = (JobList) extras.getSerializable(EXTRA_JOB);
			mType = extras.getInt(EXTRA_TYPE);
		}

		ButterKnife.bind(this);
		
		if (mType == ApplyJob.TYPE_APPLY) {
			tvApplyTitle.setText("Apply");
		} else {
			tvApplyTitle.setText("Recommend");
		}
		
		tvJobTitle.setText(mJob.getJobTitle());
	}

	@OnClick(R.id.imgbDone)
	public void onApplyButton() {
		ApplyManager applyMan = new ApplyManager();
		ApplyJob applyJob = new ApplyJob();
		applyJob.setCover_letter(edtCoverLetter.getText().toString().trim());
		applyJob.setLinkedin_url(edtLink.getText().toString().trim());
		applyJob.setJob(mJob);
		applyJob.setType(mType);
		applyJob.setStatus(BrowseItem.STATUS_SENT);

		applyMan.applyJob(applyJob,
				new NotificationReponseListener<ApplyJob>() {

					@Override
					public void requestStarted() {

					}

					@Override
					public void requestEndedWithError(VolleyError error) {

					}

					@Override
					public void requestCompleted(ApplyJob obj) {
						NotificationService notiService = new NotificationService();
						notiService
								.showNotification("Thank you for your referal. "
										+ "We will update the status of this referal by notification to you!");
						
						Toast.makeText(ApplyActivity.this, "Your application is up", Toast.LENGTH_LONG).show();
						
					}
				});
		this.finish();
	}

	@OnClick(R.id.imgbClose)
	public void onClose() {
		this.finish();
	}
}
