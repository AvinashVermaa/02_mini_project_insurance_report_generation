package com.avinash.report_generation.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurance_report")
public class InsuranceReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "plan_name")
	private String planName;

	@Column(name = "plan_status")
	private String planStatus;

	@Column(name = "gender")
	private String gender;

	@Column(name = "plan_start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planStartDate;

	@Column(name = "plan_end_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planEndDate;

	@Column(name = "ssn")
	private String ssn;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "InsuranceReport [id=" + id + ", planName=" + planName + ", planStatus=" + planStatus + ", gender="
				+ gender + ", planStartDate=" + planStartDate + ", planEndDate=" + planEndDate + ", ssn=" + ssn
				+ ", name=" + name + ", email=" + email + "]";
	}

	public InsuranceReport(String planName, String planStatus, String gender, LocalDate planStartDate,
			LocalDate planEndDate, String ssn, String name, String email) {
		super();
		this.planName = planName;
		this.planStatus = planStatus;
		this.gender = gender;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
		this.ssn = ssn;
		this.name = name;
		this.email = email;
	}

	public InsuranceReport() {
		super();
		// TODO Auto-generated constructor stub
	}

}
