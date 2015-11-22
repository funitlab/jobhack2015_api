package common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.BrowseItem;
import volley.VolleyController;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.funitlab.jobhack.models.ApplyJob;
import com.funitlab.jobhack.models.JobList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class to handle and store all Share Preference data for TIMO App
 * 
 * @author Administrator
 * 
 */
public class PreferenceHelper {

	private static final String TIMO_PREFERENCES = "timo_preference";

	private static PreferenceHelper _Instance;
	private static SharedPreferences _SharedPreferences;

	public static final String APPLY_JOB = "apply_job";
	public static final String FLAG_REWARD = "flag_reward";
	public static final String PIN_JOB = "pin_job";

	private PreferenceHelper() {
		_SharedPreferences = VolleyController.getInstance()
				.getApplicationContext()
				.getSharedPreferences(TIMO_PREFERENCES, Context.MODE_PRIVATE);
	}

	private static void initialize() {
		if (_Instance == null) {
			_Instance = new PreferenceHelper();
		}
	}

	public static final PreferenceHelper getInstance() {
		initialize();
		return _Instance;
	}

	public String getStatus() {
		// FIXME : Remove the default token here
		return _SharedPreferences.getString("a", "");
	}

	public void setStatus(String token) {
		if (_SharedPreferences == null) {
			throw new IllegalStateException(
					"SharedPreferences is not initialized");
		}
		_SharedPreferences.edit().putString("a", token).commit();
	}
	
	/**
	 * 
	 * @return true: this guy deserve a reward
	 */
	public boolean getFlagAward() {
		// FIXME : Remove the default token here
		return _SharedPreferences.getBoolean(FLAG_REWARD, false);
	}

	public void setFlagAward(boolean flg) {
		if (_SharedPreferences == null) {
			throw new IllegalStateException(
					"SharedPreferences is not initialized");
		}
		_SharedPreferences.edit().putBoolean(FLAG_REWARD, flg).commit();
	}

	/*
	 * Job Apply list
	 */
	public List<ApplyJob> getApplyJob() {
		Type t = new TypeToken<ArrayList<ApplyJob>>() {
		}.getType();
		String json = _SharedPreferences.getString(APPLY_JOB, "");
		
		Log.d("APPLY", "Job: "+json);

		List<ApplyJob> list = new Gson().fromJson(json, t);
		if (list == null) {
			list = new ArrayList<ApplyJob>();
		}
		
		// MOCKUP : Update status for job
		int random = (int )(Math.random() * 8 + 1);
		if (random == 2){
			for (int i = 0; i<list.size(); i++) {
				if (list.get(i).getStatus()<5 && list.get(i).getStatus() != BrowseItem.STATUS_FAILT){
					list.get(i).setStatus(list.get(i).getStatus()+1);
				}
			}
		}
		
		// Set fail status
//		int random2 = (int )(Math.random() * 12 + 1);
//		if (random2 == 2){
//			for (int i = 0; i<list.size(); i++) {
//				if (list.get(i).getStatus() > 1 && list.get(i).getStatus() != BrowseItem.STATUS_FAILT) {
//					list.get(i).setFailtStep(list.get(i).getStatus());
//					list.get(i).setStatus(BrowseItem.STATUS_FAILT);
//				}
//			}
//		}
		
		// Then update back to the data
		this.setApplyJobList(list);

		return list;
	}

	public void addApplyJob(ApplyJob job) {
		List<ApplyJob> list = this.getApplyJob();

		if (list == null) {
			list = new ArrayList<ApplyJob>();
		}

		// Remove if this number already in this list
		for (ApplyJob num : list) {
			if (job.equals(num)) {
				list.remove(num);
				break;
			}
		}

		// Now add to the top of the list
		list.add(0, job);

		// Save back
		this.setApplyJobList(list);
	}

	private void setApplyJobList(List<ApplyJob> list) {
		if (_SharedPreferences == null) {
			throw new IllegalStateException(
					"SharedPreferences is not initialized");
		}
		String json = new Gson().toJson(list, list.getClass());
		_SharedPreferences.edit().putString(APPLY_JOB, json).commit();
	}
	
	/*
	 * Job Pinned list
	 */
	public List<JobList> getPinJob() {
		Type t = new TypeToken<ArrayList<JobList>>() {
		}.getType();
		String json = _SharedPreferences.getString(PIN_JOB, "");

		List<JobList> list = new Gson().fromJson(json, t);
		if (list == null) {
			list = new ArrayList<JobList>();
		}

		return list;
	}

	public void addPinJob(JobList job) {
		List<JobList> list = this.getPinJob();

		if (list == null) {
			list = new ArrayList<JobList>();
		}

		// Remove if this number already in this list
		for (JobList num : list) {
			if (job.equals(num)) {
				list.remove(num);
				break;
			}
		}

		// Now add to the top of the list
		list.add(0, job);

		// Save back
		this.setPinJobList(list);
	}

	private void setPinJobList(List<JobList> list) {
		if (_SharedPreferences == null) {
			throw new IllegalStateException(
					"SharedPreferences is not initialized");
		}
		String json = new Gson().toJson(list, list.getClass());
		_SharedPreferences.edit().putString(PIN_JOB, json).commit();
	}

}
