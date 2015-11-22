package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funitlab.jobhack.R;

public class LandingPage1Fragment extends Fragment {
	private static final String TAG = "SettingFragment";

	/** CLASS **/
	Context context;

	/** PARAMETERS **/

	// newInstance constructor for creating fragment with arguments
	public static LandingPage1Fragment newInstance() {
		LandingPage1Fragment fragmentFirst = new LandingPage1Fragment();
		return fragmentFirst;
	}

	public LandingPage1Fragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get Context
		context = getActivity();

		View rootView = inflater.inflate(R.layout.fragment_landing_page1, container,
				false);

		return rootView;
	}

}
