package com.avinash.report_generation.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.avinash.report_generation.entity.InsuranceReport;
import com.avinash.report_generation.repository.InsuranceReportRepository;

@Service
public class InsuranceReportServiceImpl implements InsuranceReportService {

	private InsuranceReportRepository insuranceRepo;

	@Autowired
	public InsuranceReportServiceImpl(InsuranceReportRepository insuranceRepo) {
		System.out.println("Inside InsuranceReportServiceImpl :: constructor");
		this.insuranceRepo = insuranceRepo;
	}

	@Override
	public List<String> getPlanNamesFromDb() {
		return insuranceRepo.getPlanName();
	}

	@Override
	public List<String> getPlanStatusFromDb() {
		return insuranceRepo.getPlanStatus();
	}

	@Override
	public List<InsuranceReport> getAllInsuranceReportData() {
		return insuranceRepo.findAll();
	}

	@Override
	public List<InsuranceReport> getInsuranceReportByExample(InsuranceReport insuranceReport) {

		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<InsuranceReport> example = Example.of(insuranceReport, matcher);

		return insuranceRepo.findAll(example);

	}

	@SuppressWarnings("resource")
	@Override
	public ByteArrayInputStream generateExcel() {
		List<InsuranceReport> listInsuranceReport = insuranceRepo.findAll();

		String[] columns = { "Id", "Plan Name", "Plan Status", "Gender", "Plan Start Date", "Plan End Date", "SSN",
				"Name", "Email" };

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			XSSFSheet sheet = workbook.createSheet("data");

			XSSFRow row = sheet.createRow(0);

			for (int i = 0; i < columns.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(columns[i]);
			}

			int rowNum = 1;

			for (InsuranceReport insurance : listInsuranceReport) {
				XSSFRow row2 = sheet.createRow(rowNum);
				row2.createCell(0).setCellValue(insurance.getId());
				row2.createCell(1).setCellValue(insurance.getPlanName());
				row2.createCell(2).setCellValue(insurance.getPlanStatus());
				row2.createCell(3).setCellValue(insurance.getGender());
				row2.createCell(4).setCellValue(insurance.getPlanStartDate());
				row2.createCell(5).setCellValue(insurance.getPlanEndDate());
				row2.createCell(6).setCellValue(insurance.getSsn());
				row2.createCell(7).setCellValue(insurance.getName());
				row2.createCell(8).setCellValue(insurance.getEmail());
	
				rowNum++;

			}

			workbook.write(bos);
			return new ByteArrayInputStream(bos.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
