package fragments;

import java.util.ArrayList;

import volley.NotificationReponseListener;
import volley.VolleyController;
import adapter.BrowseAdapter;
import alertview.BrowseDialog;
import alertview.BrowseDialog.BrowseDialogInterface;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.funitlab.jobhack.ApplyActivity;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.ApplyJob;
import com.funitlab.jobhack.models.JobList;
import com.funitlab.jobhack.network.JobManager;
import common.PreferenceHelper;

public class BrowseFragment extends Fragment {
	private static final String TAG = "BrowseFragment";

	/** CLASS **/
	Context context;
	public BrowseAdapter adapter;
	ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

	/** VIEWS **/
	@Bind(R.id.lvBrowse)
	ListView lvBrowse;

	/** PARAMETERS **/
	ArrayList<JobList> browseList;
	String query = "";

	// newInstance constructor for creating fragment with arguments
	public static BrowseFragment newInstance() {
		BrowseFragment fragmentFirst = new BrowseFragment();
		return fragmentFirst;
	}

	public BrowseFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_browse, container,
				false);
		// Get Context
		context = getActivity();
		ButterKnife.bind(this, rootView);

		initViews(rootView);
		loadData();

		return rootView;
	}

	private void initViews(View view) {

		lvBrowse.setOnItemClickListener(onItemClickBrowseListViewListener);
	}

	/** DATA **/
	private void loadData() {
		if (browseList == null) {
			browseList = new ArrayList<JobList>();
		}

		getJobList();
	}

	public void filterData(String query) {
		if (adapter != null) {
			((BrowseAdapter) adapter).getFilter().filter(query);
		}
	}

	private void getJobList() {
		JobManager.getJobList(context, R.raw.job_list,
				new NotificationReponseListener<ArrayList<JobList>>() {

					@Override
					public void requestStarted() {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestEndedWithError(VolleyError error) {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestCompleted(ArrayList<JobList> obj) {

						browseList = obj;
						adapter = new BrowseAdapter(getActivity(), browseList);

						lvBrowse.setAdapter(adapter);
					}
				});
	}

	/** ACTION EVENTS **/
	private OnItemClickListener onItemClickBrowseListViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int index,
				long arg3) {
			// FIXME:
			BrowseDialog.showBrowseDialog(context, TAG, browseList.get(index),
					imageLoader, new BrowseDialogInterface() {

						@Override
						public void onRecommend(JobList job) {
							// Recommend
							Intent i = new Intent(getActivity(), ApplyActivity.class);
							i.putExtra(ApplyActivity.EXTRA_TYPE, ApplyJob.TYPE_RECOMMEND);
							i.putExtra(ApplyActivity.EXTRA_JOB, job);
							startActivity(i);
							
						}

						@Override
						public void onPin(JobList job) {
							PreferenceHelper.getInstance().addPinJob(job);
							Toast.makeText(context, "Job has been pinned!", Toast.LENGTH_LONG).show();
						}

						@Override
						public void onDismiss() {

						}

						@Override
						public void onApply(JobList job) {
							// Apply
							Intent i = new Intent(getActivity(), ApplyActivity.class);
							i.putExtra(ApplyActivity.EXTRA_TYPE, ApplyJob.TYPE_APPLY);
							i.putExtra(ApplyActivity.EXTRA_JOB, job);
							startActivity(i);
						}
					});


		}
	};
}
