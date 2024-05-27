package com.avinash.report_generation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.avinash.report_generation.service.InsuranceReportService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		//For testing Purpose:
		/*
		InsuranceReportService insuranceService = context.getBean(InsuranceReportService.class);
		System.out.println(insuranceService.getPlanNamesFromDb());
		System.out.println("---------------------------------------------------");
		System.out.println(insuranceService.getPlanStatusFromDb());
		context.close();
		*/
	}

}
