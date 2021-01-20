package com.vetologic.medbill.beans.supplierMaster;

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
@Table(name = "SUPPLIER_MASTER")
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierMasterBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUP_ID")
	private int supplierId;
	
	@Column(name = "SUP_NAME")
	private String supplierName;
	
	@Column(name = "SUP_CONTACT_NUMBER")
	private String contactNumber;
	
	@Column(name = "SUP_ADDRESS")
	private String address;
	
	@Column(name = "SUP_CONT_PRSN_NAME")
	private String contactPersonName;
	
	@Column(name = "SUP_CONT_PRSN_NUMBER")
	private String contactPersonNumber;
	
	@Column(name = "SUP_CONT_PRSN_EMAIL_ID")
	private String contactPersonEmailId;
	
	@Column(name = "SUP_DELETION_FLAG")
	private int deletionFlag;
}
