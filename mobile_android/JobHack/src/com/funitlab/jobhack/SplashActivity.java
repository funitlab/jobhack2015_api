package com.funitlab.jobhack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import animation.GifMovieView;

public class SplashActivity extends Activity {
	protected static final String TAG = "Splash Activity";
	private static long splashTime = 3000;

	protected Context context;
	GifMovieView gif1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		gif1 = (GifMovieView) findViewById(R.id.gif1);
		gif1.setMovieResource(R.drawable.loading_animation);

		context = this;

		Thread splashTimer = new Thread() {
			public void run() {
				try {
					long ms = 0;
					while (ms < splashTime) {
						sleep(100);
						ms += 100;
					}

					openLandingPage();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		};
		splashTimer.start();
	}

	private void openLandingPage() {
		Intent intent = new Intent(context, LandingPageActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);

		// Open animation
		((Activity) context).overridePendingTransition(R.anim.trans_left_in,
				R.anim.trans_left_out);
	}

}
