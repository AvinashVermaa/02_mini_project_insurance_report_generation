package com.avinash.report_generation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avinash.report_generation.entity.InsuranceReport;

@Repository
public interface InsuranceReportRepository extends JpaRepository<InsuranceReport, Integer> {

	@Query("select distinct(planName) from InsuranceReport")
	public List<String> getPlanName();
	
	@Query("select distinct(planStatus) from InsuranceReport")
	public List<String> getPlanStatus();
}
