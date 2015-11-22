package fragments;

import java.util.ArrayList;

import models.BrowseItem;

import butterknife.Bind;

import com.funitlab.jobhack.R;

import adapter.BrowseAdapter;
import adapter.HomeViewPagerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SettingFragment extends Fragment {
	private static final String TAG = "SettingFragment";

	/** CLASS **/
	Context context;

	/** PARAMETERS **/

	// newInstance constructor for creating fragment with arguments
	public static SettingFragment newInstance() {
		SettingFragment fragmentFirst = new SettingFragment();
		return fragmentFirst;
	}

	public SettingFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get Context
		context = getActivity();

		View rootView = inflater.inflate(R.layout.fragment_setting, container,
				false);

		return rootView;
	}

}
