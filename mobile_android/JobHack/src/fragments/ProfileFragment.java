package fragments;

import java.util.ArrayList;
import java.util.List;

import services.NotificationService;
import volley.NotificationReponseListener;
import volley.VolleyController;
import models.BrowseItem;
import adapter.ProfileAdapter;
import alertview.RatingDialog;
import alertview.RatingDialog.RatingDialogInterface;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.Bind;

import com.android.volley.VolleyError;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.ApplyJob;
import com.funitlab.jobhack.network.ApplyManager;

import common.PreferenceHelper;

public class ProfileFragment extends Fragment {
	private static final String TAG = "ProfileFragment";

	/** CLASS **/
	Context context;
	ProfileAdapter adapter;

	/** VIEWS **/
	@Bind(R.id.lvProfile)
	ListView lvProfile;

	/** PARAMETERS **/
	ArrayList<BrowseItem> browseList;

	List<ApplyJob> jobList;

	// newInstance constructor for creating fragment with arguments
	public static ProfileFragment newInstance() {
		ProfileFragment fragmentFirst = new ProfileFragment();
		return fragmentFirst;
	}

	public ProfileFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = getActivity();

		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);

		initViews(rootView);
		loadData();

		Log.d("Create Profile", "a");

		return rootView;
	}

	private void initViews(View view) {
		lvProfile = (ListView) view.findViewById(R.id.lvProfile);

		View header = ((Activity) context).getLayoutInflater().inflate(
				R.layout.cell_profile_header, null);
		lvProfile.addHeaderView(header);
	}

	/** DATA **/
	private void loadData() {
		if (jobList == null) {
			jobList = new ArrayList<ApplyJob>();
		}

		adapter = new ProfileAdapter(getActivity(), jobList);

		lvProfile.setAdapter(adapter);

		ApplyManager manager = new ApplyManager();
		manager.getAppliedJobs(new NotificationReponseListener<List<ApplyJob>>() {

			@Override
			public void requestStarted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void requestEndedWithError(VolleyError error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void requestCompleted(List<ApplyJob> obj) {
				// TODO Auto-generated method stub
				jobList.clear();
				jobList.addAll(obj);

				loadJobList();
			}
		});

		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Teachers",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "ic_teacher",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2, BrowseItem.STATUS_SENT));
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Mobile Developers",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "ic_developer",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2, BrowseItem.STATUS_CONTACTED));
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Designers",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "ic_designer",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2, BrowseItem.STATUS_REVIEWED));
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Engineers",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "ic_engineer",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2, BrowseItem.STATUS_INTERVIEWED));
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Mobile Developer",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "ic_accounting",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2, BrowseItem.STATUS_HIRED));
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Mobile Developer",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2, BrowseItem.STATUS_FAILT, BrowseItem.STATUS_INTERVIEWED));
		//
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Mobile Developer",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "ic_accounting",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2));
		// browseList
		// .add(new BrowseItem(
		// 1,
		// "2 Mobile Developer",
		// "Competitive $800",
		// "Funitlab Inc.",
		// "",
		// "Funitlab Inc. is searching for 2 Experience Python developer who is willing to challenge with a brand new product...",
		// 2));

	}

	private void loadJobList() {

		if (browseList == null) {
			browseList = new ArrayList<BrowseItem>();
		}
		Log.d(TAG, "Apply Job: " + jobList.size());

		// Check data to show notification
		for (int i = 0; i < jobList.size(); i++) {
			NotificationService notiService = new NotificationService();
			if (jobList.get(i).getStatus() == BrowseItem.STATUS_INTERVIEWED) {
				// Reward $5;
				notiService
						.showNotification("The Employer give you $5 thank you for your referal, a candidate has got an awesome interview.");
				PreferenceHelper.getInstance().setFlagAward(true);
				break;
			} else if (jobList.get(i).getStatus() == BrowseItem.STATUS_HIRED) {
				// Reward $50;
				if (PreferenceHelper.getInstance().getFlagAward()) {
					notiService
							.showNotification("You got the full award $50 referal.");
					PreferenceHelper.getInstance().setFlagAward(false);

					// Show the rating dialog.
					RatingDialog.showRatingDialog(getActivity(),
							"Rate the Employer",
							"How do you think about this Employer?",
							VolleyController.getInstance().getImageLoader(),
							new RatingDialogInterface() {

								@Override
								public void onOk() {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onNotNow() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onNever() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onDismiss() {
									// TODO Auto-generated method stub

								}
							});
				}
				break;
			}
		}

		adapter.notifyDataSetChanged();
	}
}
