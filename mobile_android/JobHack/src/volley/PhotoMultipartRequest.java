package volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

public class PhotoMultipartRequest<T> extends Request<T> {

	private final String FILE_PART_NAME = "image";

	private final String TAG = "Photo Multipart Request";

	private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
	private final Response.Listener<T> mListener;
	private File mImageFile;
	private List<File> mImageList;
	private String mToken;
	// protected Map<String, String> headers;
	private Map<String, String> mStringPart;

	public PhotoMultipartRequest(String url, ErrorListener errorListener,
			Listener<T> listener, File imageFile, Map<String, String> mStringPart, String token) {
		super(Method.POST, url, errorListener);

		mToken = token;
		mListener = listener;
		mImageFile = imageFile;
		this.mStringPart = mStringPart;

		if(imageFile != null){
			buildMultipartEntity();
		}
	}

	public PhotoMultipartRequest(String url, ErrorListener errorListener,
			Listener<T> listener, List<File> imageFile,
			Map<String, String> mStringPart) {
		super(Method.POST, url, errorListener);

		mListener = listener;
		mImageList = imageFile;
		this.mStringPart = mStringPart;

		buildMultipartEntitys();
	}

	public void addStringBody(String param, String value) {
		mStringPart.put(param, value);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();

		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}

		headers.put("Accept", "application/json");
		headers.put("Authorization", mToken);
//		headers.put("Content-Type", "application/json; charset=utf-8");
		
		return headers;
	}

	// Use to upload 1 image. Not in use now.
	private void buildMultipartEntity() {
		mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile,
				ContentType.create("multipart/form-data"), mImageFile.getName());
//		mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile,
//				ContentType.create("image/jpeg"), mImageFile.getName());
		mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//		String boundary = "-------------" + System.currentTimeMillis();
//		mBuilder.setBoundary(boundary);
		mBuilder.setLaxMode().setBoundary("xx")
				.setCharset(Charset.forName("UTF-8"));
	}

	// Used to upload multiple image
	private void buildMultipartEntitys() {
		// Add files to request.
		for (File file : mImageList) {
			mBuilder.addBinaryBody(FILE_PART_NAME, file,
					ContentType.create("multipart/form-data"), file.getName());
//			mBuilder.addBinaryBody(FILE_PART_NAME, file,
//					ContentType.create("image/jpeg"), file.getName());
		}
		mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		mBuilder.setLaxMode().setBoundary("xx")
				.setCharset(Charset.forName("UTF-8"));
//		mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//		String boundary = "-------------" + System.currentTimeMillis();
//		mBuilder.setBoundary(boundary);
		
		// Adding the POST parameters:
		for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
			mBuilder.addTextBody(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public String getBodyContentType() {
		String contentTypeHeader = mBuilder.build().getContentType().getValue();
		return contentTypeHeader;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			mBuilder.build().writeTo(bos);
		} catch (IOException e) {
			VolleyLog
					.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
		}

		return bos.toByteArray();
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		
		// since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
    	Log.d(TAG, "Response: "+ response.headers.toString());
		
		T result = null;
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			T t = (T) new JSONObject(jsonString);
			result = t;
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}

		return Response.success(result,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}
}