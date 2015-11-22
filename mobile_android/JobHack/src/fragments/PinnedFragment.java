package fragments;

import java.util.ArrayList;

import volley.NotificationReponseListener;
import volley.VolleyController;
import models.BrowseItem;
import butterknife.Bind;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.funitlab.jobhack.ApplyActivity;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.ApplyJob;
import com.funitlab.jobhack.models.JobList;
import com.funitlab.jobhack.network.ApplyManager;
import com.funitlab.jobhack.network.JobManager;

import common.PreferenceHelper;
import adapter.BrowseAdapter;
import adapter.HomeViewPagerAdapter;
import alertview.BrowseDialog;
import alertview.BrowseDialog.BrowseDialogInterface;
import alertview.PinnedDialog;
import alertview.PinnedDialog.PinnedDialogInterface;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PinnedFragment extends Fragment {
	private static final String TAG = "PinnedFragment";
	ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

	/** CLASS **/
	Context context;
	public BrowseAdapter adapter;

	/** VIEWS **/
	@Bind(R.id.lvPinned)
	ListView lvPinned;

	/** PARAMETERS **/
	ArrayList<JobList> browseList;

	// newInstance constructor for creating fragment with arguments
	public static PinnedFragment newInstance() {
		PinnedFragment fragmentFirst = new PinnedFragment();
		return fragmentFirst;
	}

	public PinnedFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get Context
		context = getActivity();

		View rootView = inflater.inflate(R.layout.fragment_pinned, container,
				false);

		initViews(rootView);
		loadData();

		return rootView;
	}

	private void initViews(View view) {
		lvPinned = (ListView) view.findViewById(R.id.lvPinned);
		lvPinned.setOnItemClickListener(onItemClickPinListViewListener);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Log.d(TAG, "RUN");
		refreshData();
	}
	
	/** DATA **/
	private void loadData() {
		if (browseList == null) {
			browseList = new ArrayList<JobList>();
		}else{
			browseList.clear();
		}
		adapter = new BrowseAdapter(getActivity(), browseList);
		lvPinned.setAdapter(adapter);

		getJobList();
	}

	public void refreshData(){
		loadData();
	}
	
	private void getJobList() {
		browseList.addAll((ArrayList<JobList>) PreferenceHelper.getInstance().getPinJob());
		adapter.notifyDataSetChanged();
	}
	
	/** ACTION EVENTS **/
	private OnItemClickListener onItemClickPinListViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int index,
				long arg3) {
			// FIXME:
			PinnedDialog.showPinnedDialog(context, TAG, browseList.get(index),
					imageLoader, new PinnedDialogInterface() {

						@Override
						public void onUnPin(JobList job) {
//							PreferenceHelper.getInstance().addPinJob(job);
							Toast.makeText(context, "Job has been unpinned!", Toast.LENGTH_LONG).show();
						}
						@Override
						public void onRecommend(JobList job) {
							// Recommend
							Intent i = new Intent(getActivity(), ApplyActivity.class);
							i.putExtra(ApplyActivity.EXTRA_TYPE, ApplyJob.TYPE_RECOMMEND);
							i.putExtra(ApplyActivity.EXTRA_JOB, job);
							startActivity(i);
							
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
//		JobManager.getJobList(context, R.raw.job_list,
//				new NotificationReponseListener<ArrayList<JobList>>() {
//
//					@Override
//					public void requestStarted() {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void requestEndedWithError(VolleyError error) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void requestCompleted(ArrayList<JobList> obj) {
//
//						browseList = obj;
//						adapter = new BrowseAdapter(getActivity(), browseList);
//
//						lvPinned.setAdapter(adapter);
//					}
//				});
//	}
}


