package com.cps690.ehnacefilemethods.EntityModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "login_details")
public class TestModel extends TimeStampModel {
	@Column(name = "login_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loginId;

	@Column(name = "password")
	private String password;

}
