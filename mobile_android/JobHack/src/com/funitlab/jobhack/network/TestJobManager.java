package com.funitlab.jobhack.network;

import java.util.ArrayList;

import volley.NotificationReponseListener;
import android.util.Log;

import com.android.volley.VolleyError;
import com.funitlab.jobhack.models.JobList;
import common.Common;

public class TestJobManager {
	
//	public static void main(String[] args) {
//		searchJobs();
//	}

	public void searchJobs() {
		JobManager.getJobList(Common.SEARCH_JOBS_URL,
				new NotificationReponseListener<ArrayList<JobList>>() {

					@Override
					public void requestStarted() {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestCompleted(ArrayList<JobList> obj) {
						Log.e("getJobList", "size:" + obj.size());

					}

					@Override
					public void requestEndedWithError(VolleyError error) {
						Log.e("getJobList",
								"requestEndedWithError" + error.getMessage());
						// TODO Auto-generated method stub

					}
				});
	}

	public void getListPinnedJob() {
		JobManager.getJobList(Common.LIST_PINNED_JOB_URL,
				new NotificationReponseListener<ArrayList<JobList>>() {

					@Override
					public void requestStarted() {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestCompleted(ArrayList<JobList> obj) {
						Log.e("getListPinnedJob", "size:" + obj.size());

					}

					@Override
					public void requestEndedWithError(VolleyError error) {
						Log.e("getListPinnedJob", "requestEndedWithError"
								+ error.getMessage());
						// TODO Auto-generated method stub

					}
				});
	}
}