package com.funitlab.jobhack.models;

public class ApplyJob {
	
	public static final int TYPE_APPLY = 1;
	public static final int TYPE_RECOMMEND = 2;
	
	private int type;
	private String cv_url;
	private String linkedin_url;
	private String cover_letter;
	private JobUser recommend;
	private int job_id_local;
	private JobList job;
	
	// Add status
	private int status;
	private int failtStep;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCv_url() {
		return cv_url;
	}
	public void setCv_url(String cv_url) {
		this.cv_url = cv_url;
	}
	public String getLinkedin_url() {
		return linkedin_url;
	}
	public void setLinkedin_url(String linkedin_url) {
		this.linkedin_url = linkedin_url;
	}
	public String getCover_letter() {
		return cover_letter;
	}
	public void setCover_letter(String cover_letter) {
		this.cover_letter = cover_letter;
	}
	public JobUser getRecommend() {
		return recommend;
	}
	public void setRecommend(JobUser recommend) {
		this.recommend = recommend;
	}
	public int getJob_id_local() {
		return job_id_local;
	}
	public void setJob_id_local(int job_id_local) {
		this.job_id_local = job_id_local;
	}
	public JobList getJob() {
		return job;
	}
	public void setJob(JobList job) {
		this.job = job;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFailtStep() {
		return failtStep;
	}
	public void setFailtStep(int failtStep) {
		this.failtStep = failtStep;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + job_id_local;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplyJob other = (ApplyJob) obj;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (job.getJobId() != other.job.getJobId())
			return false;
		if (job_id_local != other.job_id_local)
			return false;
		return true;
	}
	
	
}
