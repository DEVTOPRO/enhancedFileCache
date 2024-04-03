package com.cps690.ehnacefilemethods.EntityModels;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Data
@MappedSuperclass
public class TimeStampModel implements Serializable  {
	@Column(name = "created_time")
	@CreationTimestamp
	private Date createdTime;
	@Column(name = "updated_time")
	@UpdateTimestamp
	private Date updatedTime;

	@Column(name = "status")
	private Integer status;
}
