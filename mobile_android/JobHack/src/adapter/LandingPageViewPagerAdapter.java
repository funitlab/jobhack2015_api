package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fragments.LandingPage0Fragment;
import fragments.LandingPage1Fragment;
import fragments.LandingPage2Fragment;
import fragments.LandingPage3Fragment;

public class LandingPageViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;
    private static final int PROFILE_INDEX = 0;
	private static final int BROWSE_INDEX = 1;
	private static final int PINNED_INDEX = 2;
	private static final int SETTING_INDEX = 3;
	
    public LandingPageViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case PROFILE_INDEX: // Fragment # 0
            return LandingPage0Fragment.newInstance();
        case BROWSE_INDEX: // Fragment # 1
            return LandingPage1Fragment.newInstance();
        case PINNED_INDEX: // Fragment # 2
            return LandingPage2Fragment.newInstance();
        case SETTING_INDEX: // Fragment # 3
            return LandingPage3Fragment.newInstance();
        default:
            return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }


}
