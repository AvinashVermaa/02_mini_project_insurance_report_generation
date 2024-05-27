package com.avinash.report_generation.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.avinash.report_generation.bindings.ReportBinding;
import com.avinash.report_generation.entity.InsuranceReport;
import com.avinash.report_generation.service.InsuranceReportService;

import io.micrometer.common.util.StringUtils;

@Controller
public class InsuranceController {

	private InsuranceReportService insuranceReportService;

	@Autowired
	public InsuranceController(InsuranceReportService insuranceReportService) {
		System.out.println("InsuranceController :: controller");
		this.insuranceReportService = insuranceReportService;
	}

	@GetMapping("/")
	public String getIndexPage(Model model) {
		setPlanNameAndStatusInForm(model);
		return "report";
	}

	@PostMapping("/saveReportData")
	public String saveInsuranceData(@ModelAttribute("binding") ReportBinding reportBinding, Model model) {
		System.out.println(reportBinding);

		InsuranceReport insurance = new InsuranceReport();

		if (!StringUtils.isBlank(reportBinding.getPlanName())) {
			insurance.setPlanName(reportBinding.getPlanName());
		}

		if (!StringUtils.isBlank(reportBinding.getPlanStatus())) {
			insurance.setPlanStatus(reportBinding.getPlanStatus());
		}

		if (!StringUtils.isBlank(reportBinding.getGender())) {
			insurance.setGender(reportBinding.getGender());
		}

		if (reportBinding.getPlanStartDate() != null) {
			insurance.setPlanStartDate(reportBinding.getPlanStartDate());
		}

		if (reportBinding.getPlanEndDate() != null) {
			insurance.setPlanEndDate(reportBinding.getPlanEndDate());
		}

		List<InsuranceReport> listReport = insuranceReportService.getInsuranceReportByExample(insurance);

		model.addAttribute("listReport", listReport);
		setPlanNameAndStatusInForm(model);
		return "report";

	}

	private void setPlanNameAndStatusInForm(Model model) {
		List<String> planNames = insuranceReportService.getPlanNamesFromDb();
		List<String> planStatuses = insuranceReportService.getPlanStatusFromDb();
		model.addAttribute("planNames", planNames);
		model.addAttribute("planStatuses", planStatuses);

		ReportBinding binding = new ReportBinding();
		model.addAttribute("binding", binding);
	}

	@GetMapping("/downloadExcelData")
	public ResponseEntity<InputStreamResource> downloadExcelData(Model model) {
		System.out.println("Inside downloadExcelData");
		ByteArrayInputStream bis = insuranceReportService.generateExcel();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=users.xls");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(bis));

		// setPlanNameAndStatusInForm(model);
		// return "report";
	}

}
