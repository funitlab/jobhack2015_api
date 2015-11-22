package com.funitlab.jobhack;

import java.util.ArrayList;

import models.MenuItem;
import adapter.BrowseAdapter;
import adapter.HomeViewPagerAdapter;
import adapter.SliderHomeAdapter;
import alertview.LoginDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import animation.GuillotineAnimation;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.srx.widget.TabBarView;
import com.srx.widget.TabBarView.OnTabBarClickListener;
import common.Utils;

import fragments.BrowseFragment;
import fragments.PinnedFragment;
import fragments.ProfileFragment;
import fragments.SettingFragment;

/**
 * Created by Dmytro Denysenko on 5/4/15.
 */
public class HomeActivity extends FragmentActivity {
	private static final long RIPPLE_DURATION = 250;
	private static final String TAG = "HomeActivity";

	private static final int PROFILE_INDEX = 0;
	private static final int BROWSE_INDEX = 1;
	private static final int PINNED_INDEX = 2;
	private static final int SETTING_INDEX = 3;

	/** CLASS **/
	Context context;
	Fragment fragment;
	FragmentManager fragmentManager;
	GuillotineAnimation guillotineAnimation;
	SliderHomeAdapter sliderHomeAdapter;
	HomeViewPagerAdapter viewPagerAdapter;

	/** VIEWS **/
	@Bind(R.id.toolbar)
	Toolbar toolbar;
	@Bind(R.id.root)
	FrameLayout root;
	@Bind(R.id.content_hamburger)
	View contentHamburger;
	@Bind(R.id.flContainer)
	FrameLayout flContainer;
	@Bind(R.id.tvNavTitle)
	TextView tvNavTitle;
	ListView lvSliderMenu;
	@Bind(R.id.pager)
	ViewPager pager;
	@Bind(R.id.llSearchView)
	LinearLayout llSearchView;
	@Bind(R.id.edtSearch)
	EditText edtSearch;
	LinearLayout llLogin;

	/** PARAMETERS **/
	ArrayList<MenuItem> menuItemList;

	private TabBarView tabBarAnimView;
	// private ImageView imageView;
	private Drawable drawable[] = new Drawable[5];
	// private View groundView;
	private int[] location;
	private int pageTable = 0;
	private int lastPageTable = -1;
	private float maxSize = 0;
	private int initY = 0;
	private long durationMillis = 500;

	// private Animation animation = new Animation() {
	// @Override
	// protected void applyTransformation(float interpolatedTime,
	// Transformation t) {
	// float size = (maxSize - 1) * interpolatedTime + 1;
	// Matrix matrix = t.getMatrix();
	// matrix.postTranslate(location[0] - groundView.getWidth() / 2,
	// location[1] - groundView.getHeight() / 2);
	// matrix.postScale(size, size, location[0], location[1]);
	// if (interpolatedTime == 1) {
	// groundView.setVisibility(View.INVISIBLE);
	// onTabBarClickListener.onMainBtnsClick(pageTable);
	// }
	// }
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;

		ButterKnife.bind(this);
		initViews();

		initView();
		// initListener();
		initDrawable();
		initTabBarAnimView();
		onTabBarClickListener.onMainBtnsClick(pageTable);

		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// if (count == 0) {
				// imgBtnDelete.setVisibility(View.INVISIBLE);
				// } else {
				// imgBtnDelete.setVisibility(View.VISIBLE);
				// }

				if (fragment instanceof BrowseFragment) {
					String query = s.toString().toLowerCase();

					BrowseFragment browseFragment = (BrowseFragment) fragment;
					browseFragment.filterData(query);
					// BrowseAdapter adapter = browseFragment.adapter;
					// if (adapter != null) {
					// String query = s.toString().toLowerCase();
					//
					// ((BrowseAdapter) adapter).getFilter().filter(query);
					// adapter.notifyDataSetChanged();
					// }
				}

				// Hide the empty view
				// tvEmpty.setText("");
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initViews() {

		// Set text for Navigation Label
		tvNavTitle.setText(getString(R.string.hm_lb_browse));

		// Init and add Menu Action Bar
		View guillotineMenu = getLayoutInflater().inflate(R.layout.slider_menu,
				null);
		root.addView(guillotineMenu);
		lvSliderMenu = (ListView) guillotineMenu
				.findViewById(R.id.lvSliderMenu);
		llLogin = (LinearLayout) guillotineMenu.findViewById(R.id.llLogin);

		guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(
				guillotineMenu,
				guillotineMenu.findViewById(R.id.guillotine_hamburger),
				contentHamburger).setStartDelay(RIPPLE_DURATION)
				.setActionBarViewForAnimation(toolbar).setClosedOnStart(true)
				.build();

		// Set data for Slider Menu
		loadDataForSliderMenu();

		// Set event listener
		lvSliderMenu.setOnItemClickListener(onItemClickSliderMenuListener);
		pager.setOnPageChangeListener(onPageChangeLister);

		llLogin.setOnClickListener(onClickLoginListener);
	}

	/** LOAD DATA **/
	private void loadDataForSliderMenu() {
		if (menuItemList == null) {
			menuItemList = new ArrayList<MenuItem>();
		}

		// Get resource for Icons and Title
		TypedArray sliderMenuDefaultIcons = getResources().obtainTypedArray(
				R.array.slider_default_icons);
		TypedArray sliderMenuSelectedIcons = getResources().obtainTypedArray(
				R.array.slider_selected_icons);

		String[] sliderMenuTitles = getResources().getStringArray(
				R.array.slider_titles);

		for (int i = 0; i < sliderMenuTitles.length; i++) {

			boolean isSelected = false;
			if (i == BROWSE_INDEX) {
				isSelected = true;
			}

			MenuItem item = new MenuItem(sliderMenuDefaultIcons.getResourceId(
					i, 0), sliderMenuSelectedIcons.getResourceId(i, 0),
					sliderMenuTitles[i], isSelected);

			menuItemList.add(item);
		}

		sliderMenuDefaultIcons.recycle();
		sliderMenuSelectedIcons.recycle();

		sliderHomeAdapter = new SliderHomeAdapter(context, menuItemList);
		lvSliderMenu.setAdapter(sliderHomeAdapter);

		// Set selected menu item is BROWSE
		String title = menuItemList.get(BROWSE_INDEX).getTitle();
		tvNavTitle.setText(title);

		fragment = BrowseFragment.newInstance();
		fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.flContainer, fragment)
				.commit();

		// Set Adapter for View Pager
		viewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(viewPagerAdapter);
		pager.setCurrentItem(BROWSE_INDEX);

	}

	// Update Slider Menu
	private void updateSliderMenuData(long position) {

		for (int i = 0; i < menuItemList.size(); i++) {

			boolean isSelected = false;

			if (i == position) {
				isSelected = true;
			} else {
				isSelected = false;
			}

			menuItemList.get(i).setSelected(isSelected);
			Log.d(TAG, "isBOOL: "
					+ menuItemList.get((int) position).isSelected());
		}

		// Update Slider Menu
		sliderHomeAdapter.notifyDataSetChanged();
		pager.setOffscreenPageLimit(0);
	}

	/** ACTION EVENTS **/

	// ViewPager Event
	// Attach the page change listener inside the activity
	private OnPageChangeListener onPageChangeLister = new OnPageChangeListener() {

		// This method will be invoked when a new page becomes selected.
		@Override
		public void onPageSelected(int position) {
			// Toast.makeText(HomeActivity.this,
			// "Selected page position: " + position, Toast.LENGTH_SHORT)
			// .show();

			updateSelectedFragment(position);

			// Update selected Menu Item
			updateSliderMenuData(position);
		}

		// This method will be invoked when the current page is scrolled
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// Code goes here
		}

		// Called when the scroll state changes:
		// SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
		@Override
		public void onPageScrollStateChanged(int state) {
			// Code goes here
		}
	};

	/** ACTION EVENTS **/
	private OnItemClickListener onItemClickSliderMenuListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int id,
				long position) {

			updateSelectedFragment(id);

			// Close menu
			guillotineAnimation.close();

			// Update selected Menu Item
			updateSliderMenuData(position);
		}
	};

	private void updateSelectedFragment(int position) {
		pager.setCurrentItem(position);

		// Set Text for Navigation label
		String title = menuItemList.get(position).getTitle();
		tvNavTitle.setText(title);

		switch (position) {
		case PROFILE_INDEX:
			fragment = ProfileFragment.newInstance();

			break;
		case BROWSE_INDEX:
			fragment = BrowseFragment.newInstance();

			break;
		case PINNED_INDEX:
			fragment = PinnedFragment.newInstance();

			break;
		case SETTING_INDEX:
			fragment = SettingFragment.newInstance();

			break;
		default:
			break;
		}

		// Replace selected fragment
		if (fragment != null) {
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.flContainer, fragment).commit();

		}

	}

	// Back Button Clicked
	@OnClick(R.id.imgBtnBack)
	public void back(View view) {
		Utils.hideKeyboard(context);
		llSearchView.setVisibility(View.GONE);
		tabBarAnimView.setVisibility(View.VISIBLE);

	}

	// Login Button Clicked
	private OnClickListener onClickLoginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LoginDialog.showLoginDialog(context);

		}
	};

	private void initView() {
		// groundView = findViewById(R.id.groundView);
		// imageView = (ImageView) findViewById(R.id.imageView);
		tabBarAnimView = (TabBarView) findViewById(R.id.tabBarAnimView);
		initListener();
	}

	private void initListener() {
		tabBarAnimView.setOnTabBarClickListener(onTabBarClickListener);
		// animation.setDuration(durationMillis);
		// animation.setInterpolator(new DecelerateInterpolator());
	}

	private void initDrawable() {
		drawable[0] = getResources().getDrawable(R.drawable.page0);
		drawable[1] = getResources().getDrawable(R.drawable.page1);
		drawable[2] = getResources().getDrawable(R.drawable.page1);
		drawable[3] = getResources().getDrawable(R.drawable.page2);
		drawable[4] = getResources().getDrawable(R.drawable.page3);
	}

	private void initTabBarAnimView() {
		tabBarAnimView.setMainBitmap(R.drawable.ic_menu);
		tabBarAnimView.bindBtnsForPage(PROFILE_INDEX,
				R.drawable.ic_profile_active, 0, R.drawable.edit_icon);
		tabBarAnimView.bindBtnsForPage(BROWSE_INDEX,
				R.drawable.ic_browse_active, R.drawable.search_icon, 0);
		tabBarAnimView.bindBtnsForPage(PINNED_INDEX,
				R.drawable.ic_pinned_active, R.drawable.search_icon, 0);
		tabBarAnimView.bindBtnsForPage(SETTING_INDEX,
				R.drawable.ic_settings_active, 0, R.drawable.edit_icon);
		tabBarAnimView.initializePage(BROWSE_INDEX);
	}

	private OnTabBarClickListener onTabBarClickListener = new OnTabBarClickListener() {

		@Override
		public void onMainBtnsClick(int position, int[] clickLocation) {

			if (lastPageTable == position)
				return;
			if (position < 5) {
				clickLocation[1] = clickLocation[1] - initY;
				location = clickLocation;
				pageTable = position;
				// groundView.setVisibility(View.VISIBLE);
				// groundView.startAnimation(animation);

				switch (position) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:
					position = PINNED_INDEX;

					break;
				case 4:
					position = SETTING_INDEX;
					break;
				default:
					break;
				}

				Log.e(TAG, "onMainBtnsClick--->" + position);
				updateSelectedFragment(position);

				// Update selected Menu Item
				updateSliderMenuData(position);
			}
			lastPageTable = position;
		}

		@Override
		public void onMainBtnsClick(int position) {
			// imageView.setImageDrawable(drawable[position]);
			Log.e(TAG, "center--->" + position);

		}

		@Override
		public void onLeftBtnClick(int page) {
			Log.e(TAG, "left--->" + page);
			switch (page) {
			case BROWSE_INDEX:
				edtSearch.requestFocus();
				llSearchView.setVisibility(View.VISIBLE);
				tabBarAnimView.setVisibility(View.INVISIBLE);

				Utils.showKeyboard(context, edtSearch);
				break;

			default:
				break;
			}
		}

		@Override
		public void onRightBtnClick(int page) {
			Log.e(TAG, "right--->" + page);
		}

	};

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		int local[] = new int[2];
		// groundView.getLocationOnScreen(local);
		initY = local[1];
		// maxSize = 2f * (float) (((ViewGroup) groundView.getParent())
		// .getHeight() / groundView.getHeight());
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
