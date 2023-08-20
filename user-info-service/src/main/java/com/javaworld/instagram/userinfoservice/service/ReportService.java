package com.javaworld.instagram.userinfoservice.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private DataSource datasource;

	public void generateReport(String username) {
		try {

			// Load the .jrxml file from the classpath
			InputStream reportStream = new ClassPathResource("/reports/acoount-information.jrxml").getInputStream();

			// Compile the .jrxml file into a JasperDesign object
			JasperDesign jasperDesign = JRXmlLoader.load(reportStream);

			// Compile the JasperDesign into a JasperReport object
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			// Create the JasperPrint object using your data source
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getReportParameters(),
					datasource.getConnection());
			
			File reportsDir = new File("/reports");
			
			if(!reportsDir.exists()) {
				if(reportsDir.mkdir()) {
					logger.info("reports directory created");
				}
			}

			// Export the report to PDF, HTML, etc.
			JasperExportManager.exportReportToPdfFile(jasperPrint, "/reports/" + username + ".pdf");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private Map<String, Object> getReportParameters() {
		// Set any report parameters you need
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Parameter1", "Value1");
		parameters.put("Parameter2", "Value2");
		return parameters;
	}

}