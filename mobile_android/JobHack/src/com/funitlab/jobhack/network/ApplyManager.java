package com.funitlab.jobhack.network;

import java.util.List;

import volley.AppController;
import volley.NotificationReponseListener;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.funitlab.jobhack.models.ApplyJob;

import common.PreferenceHelper;

public class ApplyManager {
	
	public ApplyManager() {
		
	}
	
	public void applyJob(ApplyJob job, final NotificationReponseListener<ApplyJob> listener) {
		
		listener.requestStarted();
		// Save to share preference.
		PreferenceHelper.getInstance().addApplyJob(job);
		
		// All so call a api to server to send a mail to employer
		// Create the mail to send here.
		String mailContent = "Dear Hung,\n"
				+ "JOB HACK has a new candidate for your job "
				+ job.getJob().getJobTitle()
				+ ". Please find more detail about this candidate below: \n"
				+ "LinkedIn Link: " + job.getLinkedin_url()
				+ "\n You can access our Control Panel to get more information about this candiate and continue process your recruitment process"
				+ "\n Thank you and best regards,"
				+ "\n JOB HACK team,";
//		sendMailApply(mailContent);
		
		listener.requestCompleted(job);
	}
	
	public void getAppliedJobs(final NotificationReponseListener<List<ApplyJob>> listener) {
		
		listener.requestStarted();
		
		// Go to share preference to get it.
		List<ApplyJob> list = PreferenceHelper.getInstance().getApplyJob();
		
		listener.requestCompleted(list);
	}
	
	private void sendMailApply(String mail) {
		String url = "";

		// Creating Volley request for JSON
		StringRequest postRequest = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("Response", "===>Competed: " + response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.d("ERROR", "error => " + error.toString());
					}

				}) {
			
		};

		// Adding request to request queue
		RequestQueue queue = Volley.newRequestQueue(AppController.getInstance().getApplicationContext());
		queue.add(postRequest);
	}
}
