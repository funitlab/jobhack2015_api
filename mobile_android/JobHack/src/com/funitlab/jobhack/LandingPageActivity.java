package com.funitlab.jobhack;

import adapter.LandingPageViewPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingPageActivity extends FragmentActivity {

	LandingPageViewPagerAdapter adapter;
	
	@Bind(R.id.pager)
	ViewPager pager;
	
	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_landing_page);

		ButterKnife.bind(this);

		// Set Adapter for View Pager
		adapter = new LandingPageViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		
		pager.setCurrentItem(0);
		
		pager.setOnPageChangeListener(onPageChangeLister);

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
	
	@OnClick(R.id.btnJoinNow)
	public void joinNow(View v){
		openHome();
	}
	
	private void openHome() {
		Intent intent = new Intent(LandingPageActivity.this, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);

		// Open animation
		(LandingPageActivity.this).overridePendingTransition(R.anim.trans_left_in,
				R.anim.trans_left_out);
	}
}
