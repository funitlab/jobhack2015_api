package com.funitlab.jobhack.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945762804977405624L;
	@SerializedName("job_id")
	private int jobId;
	@SerializedName("job_company")
	private String jobCompany;
	@SerializedName("job_title")
	private String jobTitle;
	@SerializedName("salary_min")
	private String salaryMin;
	@SerializedName("salary_max")
	private String salaryMax;
	@SerializedName("job_detail_url")
	private String jobDetailUrl;
	@SerializedName("job_description")
	private String jobDescription;
	@SerializedName("job_logo_url")
	private String jobLogoUrl;
	@SerializedName("skills")
	@Expose
	private List<String> skills = new ArrayList<String>();
	@SerializedName("benefits")
	private List<String> benefits = new ArrayList<String>();

	/**
	 * 
	 * @return The jobId
	 */
	public int getJobId() {
		return jobId;
	}

	/**
	 * 
	 * @param jobId
	 *            The job_id
	 */
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public JobList withJobId(int jobId) {
		this.jobId = jobId;
		return this;
	}

	/**
	 * 
	 * @return The jobCompany
	 */
	public String getJobCompany() {
		return jobCompany;
	}

	/**
	 * 
	 * @param jobCompany
	 *            The job_company
	 */
	public void setJobCompany(String jobCompany) {
		this.jobCompany = jobCompany;
	}

	public JobList withJobCompany(String jobCompany) {
		this.jobCompany = jobCompany;
		return this;
	}

	/**
	 * 
	 * @return The jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * 
	 * @param jobTitle
	 *            The job_title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public JobList withJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
		return this;
	}

	/**
	 * 
	 * @return The salaryMin
	 */
	public String getSalaryMin() {
		return salaryMin;
	}

	/**
	 * 
	 * @param salaryMin
	 *            The salary_min
	 */
	public void setSalaryMin(String salaryMin) {
		this.salaryMin = salaryMin;
	}

	public JobList withSalaryMin(String salaryMin) {
		this.salaryMin = salaryMin;
		return this;
	}

	/**
	 * 
	 * @return The salaryMax
	 */
	public String getSalaryMax() {
		return salaryMax;
	}

	/**
	 * 
	 * @param salaryMax
	 *            The salary_max
	 */
	public void setSalaryMax(String salaryMax) {
		this.salaryMax = salaryMax;
	}

	public JobList withSalaryMax(String salaryMax) {
		this.salaryMax = salaryMax;
		return this;
	}

	/**
	 * 
	 * @return The jobDetailUrl
	 */
	public String getJobDetailUrl() {
		return jobDetailUrl;
	}

	/**
	 * 
	 * @param jobDetailUrl
	 *            The job_detail_url
	 */
	public void setJobDetailUrl(String jobDetailUrl) {
		this.jobDetailUrl = jobDetailUrl;
	}

	public JobList withJobDetailUrl(String jobDetailUrl) {
		this.jobDetailUrl = jobDetailUrl;
		return this;
	}

	/**
	 * 
	 * @return The jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}

	/**
	 * 
	 * @param jobDescription
	 *            The job_description
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public JobList withJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
		return this;
	}

	/**
	 * 
	 * @return The jobLogoUrl
	 */
	public String getJobLogoUrl() {
		return jobLogoUrl;
	}

	/**
	 * 
	 * @param jobLogoUrl
	 *            The job_logo_url
	 */
	public void setJobLogoUrl(String jobLogoUrl) {
		this.jobLogoUrl = jobLogoUrl;
	}

	public JobList withJobLogoUrl(String jobLogoUrl) {
		this.jobLogoUrl = jobLogoUrl;
		return this;
	}

	/**
	 * 
	 * @return The skills
	 */
	public List<String> getSkills() {
		return skills;
	}

	/**
	 * 
	 * @param skills
	 *            The skills
	 */
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public JobList withSkills(List<String> skills) {
		this.skills = skills;
		return this;
	}

	/**
	 * 
	 * @return The benefits
	 */
	public List<String> getBenefits() {
		return benefits;
	}

	/**
	 * 
	 * @param benefits
	 *            The benefits
	 */
	public void setBenefits(List<String> benefits) {
		this.benefits = benefits;
	}

	public JobList withBenefits(List<String> benefits) {
		this.benefits = benefits;
		return this;
	}

	@Override
	public String toString() {
		return "JobList [jobId=" + jobId + ", jobCompany=" + jobCompany
				+ ", jobTitle=" + jobTitle + ", salaryMin=" + salaryMin
				+ ", salaryMax=" + salaryMax + ", jobDetailUrl=" + jobDetailUrl
				+ ", jobDescription=" + jobDescription + ", jobLogoUrl="
				+ jobLogoUrl + ", skills=" + skills + ", benefits=" + benefits
				+ "]";
	}

}
