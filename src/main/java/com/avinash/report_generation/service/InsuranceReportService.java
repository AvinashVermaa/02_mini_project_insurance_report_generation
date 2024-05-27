package com.avinash.report_generation.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.avinash.report_generation.entity.InsuranceReport;

public interface InsuranceReportService {

	public List<String> getPlanNamesFromDb();
	
	public List<String> getPlanStatusFromDb();
	
	public List<InsuranceReport> getAllInsuranceReportData();
	
	public List<InsuranceReport> getInsuranceReportByExample(InsuranceReport insuranceReport);
	
	public ByteArrayInputStream generateExcel();
}
