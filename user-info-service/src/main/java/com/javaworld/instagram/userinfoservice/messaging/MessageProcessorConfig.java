package com.javaworld.instagram.userinfoservice.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javaworld.instagram.commonlib.exception.EventProcessingException;
import com.javaworld.instagram.commonlib.messaging.Event;
import com.javaworld.instagram.userinfoservice.service.ReportService;

@Configuration
public class MessageProcessorConfig {

	private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorConfig.class);

	@Autowired
	private ReportService reportService;

	@Bean
	public Consumer<Event<String, Object>> messageProcessor() {

		return event -> { 

			LOG.info("Process message created at {}...", event.getEventCreatedAt());

			switch (event.getEventType()) {

			case GENERATE_USER_ACCOUNT_INFORMATION_REPORT:
				String username = event.getKey();
				LOG.info("Generate account information report for username: ", username);
				reportService.generateReport(username);
				break;

			default:
				String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a GENERATE_USER_ACCOUNT_INFORMATION_REPORT event";
				LOG.warn(errorMessage);
				throw new EventProcessingException(errorMessage);
			}

			LOG.info("Message processing done!");
		};
	}
}