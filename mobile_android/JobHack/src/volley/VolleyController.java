package volley;

import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.oauth.OAuthService;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import common.Common;

/*
 * Reference from this: http://www.androidhive.info/2014/05/android-working-with-volley-library-1/
 * Need to improve: https://gist.github.com/lezorich/8f3f3a54f07515881581
 * 
 */
public class VolleyController extends Application {

	public static final String TAG = VolleyController.class.getSimpleName();
	// private static final String SET_COOKIE_KEY = "Set-Cookie";
	// private static final String COOKIE_KEY = "smap_session";
	// private static final String SESSION_COOKIE = "smap_cookie";

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static VolleyController mInstance;
	private static OAuthService mOAuthService;

	// private RequestQueue _requestQueue;
	// private SharedPreferences _preferences;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		mOAuthService = new ServiceBuilder().provider(LinkedInApi.class)
				.apiKey(Common.API_KEY).apiSecret(Common.SECRET).build();
	}

	public static synchronized OAuthService getOAuthServiceInstance() {
		return mOAuthService;
	}

	public static synchronized VolleyController getInstance() {
		return mInstance;
	}

	public static VolleyController get() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	/**
	 * Checks the response headers for session cookie and saves it if it finds
	 * it.
	 * 
	 * @param headers
	 *            Response Headers.
	 */
	public final void checkSessionCookie(Map<String, String> headers) {
		// if (headers.containsKey(SET_COOKIE_KEY)
		// && headers.get(SET_COOKIE_KEY).startsWith(COOKIE_KEY)) {
		// String cookie = headers.get(SET_COOKIE_KEY);
		// if (cookie.length() > 0) {
		// String[] splitCookie = cookie.split(";");
		// String[] splitSessionId = splitCookie[0].split("=");
		// cookie = splitSessionId[1];
		// Editor prefEditor = _preferences.edit();
		// prefEditor.putString(SESSION_COOKIE, cookie);
		// prefEditor.commit();
		// }
		// }
	}

	/**
	 * Adds session cookie to headers if exists.
	 * 
	 * @param headers
	 */
	public final void addSessionCookie(Map<String, String> headers) {
		// String sessionId = _preferences.getString(SESSION_COOKIE, "sfsdf");
		// Log.d(TAG, "Session ID: "+ sessionId);
		// if (sessionId.length() > 0) {
		// StringBuilder builder = new StringBuilder();
		// builder.append(COOKIE_KEY);
		// builder.append("=");
		// builder.append(sessionId);
		// if (headers.containsKey(COOKIE_KEY)) {
		// builder.append("; ");
		// builder.append(headers.get(COOKIE_KEY));
		// }
		// headers.put(SET_COOKIE_KEY, builder.toString());
		// }
	}
}
