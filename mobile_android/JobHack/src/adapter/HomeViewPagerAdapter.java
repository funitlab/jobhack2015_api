package adapter;

import fragments.BrowseFragment;
import fragments.PinnedFragment;
import fragments.ProfileFragment;
import fragments.SettingFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;
    private static final int PROFILE_INDEX = 0;
	private static final int BROWSE_INDEX = 1;
	private static final int PINNED_INDEX = 2;
	private static final int SETTING_INDEX = 3;
	
    public HomeViewPagerAdapter(FragmentManager fragmentManager) {
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
            return ProfileFragment.newInstance();
        case BROWSE_INDEX: // Fragment # 1
            return BrowseFragment.newInstance();
        case PINNED_INDEX: // Fragment # 2
            return PinnedFragment.newInstance();
        case SETTING_INDEX: // Fragment # 2
            return SettingFragment.newInstance();
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
