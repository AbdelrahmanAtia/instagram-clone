package com.javaworld.instagram.userinfoservice.service.criteria;

import tech.jhipster.service.filter.StringFilter;

public class UserCriteria {

	private StringFilter name;

	private StringFilter fullName;

	public StringFilter getName() {
		return name;
	}

	public void setName(StringFilter name) {
		this.name = name;
	}

	public StringFilter getFullName() {
		return fullName;
	}

	public void setFullName(StringFilter fullName) {
		this.fullName = fullName;
	}

}
