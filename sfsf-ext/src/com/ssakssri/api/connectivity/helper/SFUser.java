package com.ssakssri.api.connectivity.helper;

import com.sap.hana.cloud.samples.benefits.persistence.model.User;

public class SFUser {

	public String userId;
	public String username;
	public String displayName;
	public String email;
	public String title;
	public String location;
	public String division;
	public String department;
	public String custom01;
	public String custom02;
	public String custom03;
	public String custom04;
	public String custom05;
	public String custom06;
	public String businessPhone;
	public String cellPhone;
	public String gender;
	public String hireDate;
	public String dateOfBirth;
	public String firstName;
	public String lastName;
	public SFUser hr;

	public SFUser() {

	}

	public void write(User user) {
		user.setUsername(username);
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
	}

}
