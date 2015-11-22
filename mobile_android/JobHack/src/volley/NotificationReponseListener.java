package volley;

import com.android.volley.VolleyError;

public interface NotificationReponseListener<T> {
	public void requestStarted();

	public void requestCompleted(T obj);

	public void requestEndedWithError(VolleyError error);

}
