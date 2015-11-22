package volley;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

/*
 * Reference from: http://stackoverflow.com/questions/16680701/using-cookies-with-android-volley-library/25388897#25388897
 */
public class StringRequest extends com.android.volley.toolbox.StringRequest {

	// private static final String TAG = "StringRequest";
	// private final Map<String, String> _params;

	/**
	 * @param method
	 * @param url
	 * @param params
	 *            A {@link HashMap} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 * @param errorListener
	 */
	public StringRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android
	 * .volley.NetworkResponse)
	 */
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		// since we don't know which of the two underlying network vehicles
		// will Volley use, we have to handle and store session cookies manually
		// Log.d(TAG, "Response: "+ response.headers.toString());
		// VolleyController.get().checkSessionCookie(response.headers);

		return super.parseNetworkResponse(response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Request#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();

		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}

		VolleyController.get().addSessionCookie(headers);

		return headers;
	}
}
