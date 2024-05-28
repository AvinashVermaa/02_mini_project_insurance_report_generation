package com.avinash.report_generation.utility;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.avinash.report_generation.service.InsuranceReportService;

import jakarta.mail.internet.MimeMessage;

@Component
public class ReportEmailSender {

	private JavaMailSender mailSender;
	private InsuranceReportService insuranceService;

	@Autowired
	public ReportEmailSender(JavaMailSender mailSender, InsuranceReportService insuranceService) {
		System.out.println("ReportEmailSender :: constructor");
		this.mailSender = mailSender;
		this.insuranceService = insuranceService;
	}

	public void sendEmailWithExcelAttachment(ByteArrayInputStream bis, int state) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo("bhaidabangg32@gmail.com");
			helper.setSubject("Insurance Report Generation : ");
			helper.setText("Please find attached report.");

			if (state == 1) {
				helper.addAttachment("data.xls", new ByteArrayResource(bis.readAllBytes()));
			} else {
				helper.addAttachment("data.pdf", new ByteArrayResource(bis.readAllBytes()));
			}

			mailSender.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
