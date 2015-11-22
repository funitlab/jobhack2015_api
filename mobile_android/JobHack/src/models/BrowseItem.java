package models;

import com.funitlab.jobhack.models.ApplyJob;

public class BrowseItem {
	public static final int STATUS_FAILT = -1;
	public static final int STATUS_UNCOMPLETE = 0;
	public static final int STATUS_SENT = 1;
	public static final int STATUS_REVIEWED = 2;
	public static final int STATUS_CONTACTED = 3;
	public static final int STATUS_INTERVIEWED = 4;
	public static final int STATUS_HIRED = 5;

	private int id;
	private String jobName;
	private String salary;
	private String companyName;
	private String jobIcon;
	private String description;
	private int starNumber;
	private int status;
	private int failtStep;

	public BrowseItem() {
	}
	
	public BrowseItem(ApplyJob apply) {
		super();
		this.id = apply.getJob().getJobId();
		this.jobName = apply.getJob().getJobTitle();
		this.salary = apply.getJob().getSalaryMin();
		this.companyName = apply.getJob().getJobCompany();
		this.jobIcon = apply.getJob().getJobLogoUrl();
		this.description = apply.getJob().getJobDescription();
		this.starNumber = 4;
		this.status = apply.getStatus();
		this.failtStep = apply.getFailtStep();
	}

	public BrowseItem(int id, String jobName, String salary,
			String companyName, String jobIcon, String description,
			int starNumber) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.salary = salary;
		this.companyName = companyName;
		this.jobIcon = jobIcon;
		this.description = description;
		this.starNumber = starNumber;
	}

	public BrowseItem(int id, String jobName, String salary,
			String companyName, String jobIcon, String description,
			int starNumber, int status) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.salary = salary;
		this.companyName = companyName;
		this.jobIcon = jobIcon;
		this.description = description;
		this.starNumber = starNumber;
		this.status = status;
	}

	public BrowseItem(int id, String jobName, String salary,
			String companyName, String jobIcon, String description,
			int starNumber, int status, int failtStep) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.salary = salary;
		this.companyName = companyName;
		this.jobIcon = jobIcon;
		this.description = description;
		this.starNumber = starNumber;
		this.status = status;
		this.failtStep = failtStep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobIcon() {
		return jobIcon;
	}

	public void setJobIcon(String jobIcon) {
		this.jobIcon = jobIcon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStarNumber() {
		return starNumber;
	}

	public void setStarNumber(int starNumber) {
		this.starNumber = starNumber;
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

}
