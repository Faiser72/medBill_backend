package com.vetologic.medbill.utils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class AbstractCreatedAndUpdated {

	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "UPDATED_DATE")
	private String updatedDate;
}
