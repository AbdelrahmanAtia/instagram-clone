package com.javaworld.instagram.commonlib.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig implements BeanPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(BeanConfig.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		logger.debug("Before initializing bean {}", beanName);
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		logger.debug("After initializing bean {}", beanName);
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

}
