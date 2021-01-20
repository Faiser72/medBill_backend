package com.vetologic.medbill.beans.manufacturerMaster;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "MANUFACTURER_MASTER")
@Data
@EqualsAndHashCode(callSuper = false)
public class ManufacturerMasterBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MGFR_ID")
	private int manufacturerId;
	
	@Column(name = "MGFR_NAME")
	private String manufacturerName;
	
	@Column(name = "MGFR_CONTACT_NUM")
	private String contactNumber;
	
	@Column(name = "MGFR_CONT_PRSN_NAME")
	private String contactPersonName;
	
	@Column(name = "MGFR_ADDRESS")
	private String address;
	
	@Column(name = "MGFR_CONT_PRSN_NUM")
	private String contactPersonNumber;
	
	@Column(name = "MGFR_CONT_PRSN_EMAIL_ID")
	private String contactPersonEmailId;
	
	@Column(name = "MGFR_DELETION_FLAG")
	private int deletionFlag;
}
