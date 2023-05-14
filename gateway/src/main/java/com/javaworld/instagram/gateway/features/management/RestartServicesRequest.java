package com.javaworld.instagram.gateway.features.management;

import java.util.List;

public class RestartServicesRequest {
	
	private List<ServiceName> serviceNames;

	public List<ServiceName> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<ServiceName> serviceNames) {
		this.serviceNames = serviceNames;
	}
}
