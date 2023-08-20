package com.javaworld.instagram.userinfoservice.restapi;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.javaworld.instagram.userinfoservice.server.api.ReportsApi;
import com.javaworld.instagram.userinfoservice.service.ReportService;

public class ReportsApiImpl implements ReportsApi {

	@Autowired
	private ReportService reportService;

	@Override
	public Resource downloadAccountInformationReport(String userName) {

		reportService.generateReport(userName);

		Resource resource = new FileSystemResource(Paths.get("/reports/" + userName + ".pdf").toFile());

		return resource;

	}

}
