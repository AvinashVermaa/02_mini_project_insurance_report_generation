package com.avinash.report_generation.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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

	@Override
	public ByteArrayInputStream generatePdf() {
		List<InsuranceReport> listInsurance = insuranceRepo.findAll();
		Document document = new Document();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, bos);
			document.open();

			// Add Title
			Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
			Paragraph para = new Paragraph("Insurance Report Generation Report Generated at : "+new Date(), font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(new Paragraph(" ")); // Add a blank line

			// Add Table
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);

			// Table Header
			Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("ID", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Plan Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Plan Status", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gender", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Plan Start Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Plan End Date", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SSN", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Email", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			// Table Data
			for (InsuranceReport data : listInsurance) {
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(data.getId().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getPlanName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getPlanStatus()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getGender()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getPlanStartDate().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getPlanEndDate().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getSsn()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(data.getEmail()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}

			
			
			
			document.add(table);
			document.add(new Paragraph(" ")); // Add a blank line
			Paragraph para1 = new Paragraph("Developed by Avinash Verma Sr.Software Engineer", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para1);
			
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(bos.toByteArray());
	}

}
