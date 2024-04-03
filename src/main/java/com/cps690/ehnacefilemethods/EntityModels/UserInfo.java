package com.cps690.ehnacefilemethods.EntityModels;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "user_infor")
public class UserInfo extends TimeStampModel{
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "mobile_number")
	private long mobileNumber;
	@Column(name = "mail_id")
	private String email;
	@Column(name = "age")
	private int age;
	@Column(name = "gender")
	private String gender;
	@Column(name = "address")
	private String address;
	@Column(name = "blood_group")
	private String bloodGroup;
	@Column(name = "country")
	private String country;
	@Column(name = "state")
	private String state;

}
