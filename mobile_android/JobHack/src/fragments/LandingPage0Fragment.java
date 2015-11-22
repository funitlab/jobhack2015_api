package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funitlab.jobhack.R;

public class LandingPage0Fragment extends Fragment {
	private static final String TAG = "SettingFragment";

	/** CLASS **/
	Context context;

	/** PARAMETERS **/

	// newInstance constructor for creating fragment with arguments
	public static LandingPage0Fragment newInstance() {
		LandingPage0Fragment fragmentFirst = new LandingPage0Fragment();
		return fragmentFirst;
	}

	public LandingPage0Fragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get Context
		context = getActivity();

		View rootView = inflater.inflate(R.layout.fragment_landing_page0, container,
				false);

		return rootView;
	}

}
