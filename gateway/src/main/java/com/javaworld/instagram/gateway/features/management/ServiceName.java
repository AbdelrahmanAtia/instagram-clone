package com.javaworld.instagram.gateway.features.management;

public enum ServiceName {
	POST_MS("post-ms"), 
	USER_MS("user-ms");

	private String name;

	ServiceName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static ServiceName fromString(String name) {
		for (ServiceName serviceName : ServiceName.values()) {
			if (serviceName.getName().equalsIgnoreCase(name)) {
				return serviceName;
			}
		}
		throw new IllegalArgumentException("Invalid service name: " + name);
	}
}
