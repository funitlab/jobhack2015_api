package com.funitlab.jobhack.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import volley.AppController;
import volley.NotificationReponseListener;
import volley.VolleyController;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.funitlab.jobhack.R;
import com.funitlab.jobhack.models.JobList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JobManager {
	public static void getJobList(String url,
			final NotificationReponseListener<ArrayList<JobList>> listener) {
		listener.requestStarted();
		StringRequest postRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String Common) {

						Gson gson = new Gson();
						JsonParser parser = new JsonParser();
						JsonArray jArray = parser.parse(Common)
								.getAsJsonArray();

						ArrayList<JobList> result = new ArrayList<JobList>();

						for (JsonElement obj : jArray) {
							JobList cse = gson.fromJson(obj, JobList.class);
							result.add(cse);
						}

						Log.d("Request COmpleted", result.toString());

						listener.requestCompleted(result);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("Request COmpleted", "Loi: " + error.getMessage());

						listener.requestEndedWithError(error);
					}
				});

		VolleyController.getInstance().addToRequestQueue(postRequest);
	}

	public static void getJobList(final Context context, final int fileRawId,
			final NotificationReponseListener<ArrayList<JobList>> listener) {
		listener.requestStarted();
		String Common = "";
		InputStream is = context.getResources().openRawResource(fileRawId);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int n;
			try {
				while ((n = reader.read(buffer)) != -1) {
					try {
						writer.write(buffer, 0, n);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Common = writer.toString();
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				JsonArray jArray = parser.parse(Common).getAsJsonArray();

				ArrayList<JobList> result = new ArrayList<JobList>();

				for (JsonElement obj : jArray) {
					JobList cse = gson.fromJson(obj, JobList.class);
					result.add(cse);
				}

				Log.d("Request COmpleted", result.toString());

				listener.requestCompleted(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

	}
}
