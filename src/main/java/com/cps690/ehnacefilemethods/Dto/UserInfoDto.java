package com.cps690.ehnacefilemethods.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
public class UserInfoDto {
	private long id;
	private String fullName;
	private long mobileNumber;
	private String email;
	private int age;
	private String gender;
	private String address;
	private String bloodGroup;
	private String country;
	private String state;
}
