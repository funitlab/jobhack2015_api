package adapter;

import java.util.ArrayList;
import java.util.List;

import volley.CircleNetworkImageView;
import volley.VolleyController;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.android.volley.toolbox.ImageLoader;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.JobList;
import common.JobHackImageDrawable;
import fragments.BrowseFragment;

public class BrowseAdapter extends BaseAdapter implements Filterable {
	private static final String TAG = "BrowseAdapter";

	// CLASS
	private Context context;

	// VIEWS
	private LayoutInflater inflater;
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

	// PARAMETERS
	private List<JobList> jobList;
	private List<JobList> stored;

	ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

	public BrowseAdapter(Context context, List<JobList> JobList) {
		this.context = context;
		this.jobList = JobList;
		stored = JobList;

	}

	@Override
	public int getCount() {
		return jobList.size();
	}

	@Override
	public Object getItem(int location) {
		return jobList.get(location);
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
			convertView = inflater.inflate(R.layout.cell_browse, null);

		if (imageLoader == null)
			imageLoader = VolleyController.getInstance().getImageLoader();

		ButterKnife.bind(this, convertView);

		Log.d(TAG, "SIZE df: " + jobList.size());

		JobList item = jobList.get(position);

		tvJobName.setText(item.getJobTitle());
		tvCompanyName.setText(item.getJobCompany());

		if (item.getJobLogoUrl() != null && item.getJobLogoUrl().length() != 0) {
			imgJobIcon
					.setImageUrl(
							item.getJobLogoUrl(),
							imageLoader);

		} else {
			Resources resources = context.getResources();
			final int resourceId = resources.getIdentifier("ic_developer",
					"drawable", context.getPackageName());
			Drawable iconDrawable = resources.getDrawable(resourceId);

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

		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				ArrayList<JobList> FilteredArrayJobTitle = new ArrayList<JobList>();

				for (int i = 0; i < stored.size(); i++) {
					JobList item = stored.get(i);

					String title = item.getJobTitle();
					constraint = constraint.toString().toLowerCase();

					if (title.toLowerCase().contains(constraint.toString())) {

						FilteredArrayJobTitle.add(item);
					}
				}

				results.count = FilteredArrayJobTitle.size();
				results.values = FilteredArrayJobTitle;
				return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				if (results.count == 0) {
					notifyDataSetInvalidated();
				} else {
					jobList = null;
					jobList = (ArrayList<JobList>) results.values;
					notifyDataSetChanged();
				}

				Log.d(TAG, "SIZE: " + jobList.size());

			}

		};

		return filter;
	}

}
