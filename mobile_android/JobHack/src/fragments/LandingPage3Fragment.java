package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funitlab.jobhack.R;

public class LandingPage3Fragment extends Fragment {
	private static final String TAG = "SettingFragment";

	/** CLASS **/
	Context context;

	/** PARAMETERS **/

	// newInstance constructor for creating fragment with arguments
	public static LandingPage3Fragment newInstance() {
		LandingPage3Fragment fragmentFirst = new LandingPage3Fragment();
		return fragmentFirst;
	}

	public LandingPage3Fragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get Context
		context = getActivity();

		View rootView = inflater.inflate(R.layout.fragment_landing_page3, container,
				false);

		return rootView;
	}

}
