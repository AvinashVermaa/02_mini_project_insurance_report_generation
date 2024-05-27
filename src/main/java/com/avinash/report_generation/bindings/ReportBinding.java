package com.avinash.report_generation.bindings;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ReportBinding {

	private String planName;
	private String planStatus;
	private String gender;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planStartDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planEndDate;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(LocalDate planStartDate) {
		this.planStartDate = planStartDate;
	}

	public LocalDate getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(LocalDate planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Override
	public String toString() {
		return "ReportBinding [planName=" + planName + ", planStatus=" + planStatus + ", gender=" + gender
				+ ", planStartDate=" + planStartDate + ", planEndDate=" + planEndDate + "]";
	}

	public ReportBinding(String planName, String planStatus, String gender, LocalDate planStartDate,
			LocalDate planEndDate) {
		super();
		this.planName = planName;
		this.planStatus = planStatus;
		this.gender = gender;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
	}

	public ReportBinding() {
		super();
		// TODO Auto-generated constructor stub
	}

}
