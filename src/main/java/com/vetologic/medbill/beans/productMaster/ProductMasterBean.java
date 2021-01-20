package com.vetologic.medbill.beans.productMaster;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vetologic.medbill.beans.manufacturerMaster.ManufacturerMasterBean;
import com.vetologic.medbill.beans.productCategoryMaster.ProductCategoryMasterBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PRODUCT_MASTER")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductMasterBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROD_ID")
	private int productId;
	
	@Column(name = "PROD_NAME")
	private String productName;
	
	@ManyToOne
	@JoinColumn(name = "PROD_CAT_ID")
	private ProductCategoryMasterBean productCategory;
	
	@ManyToOne
	@JoinColumn(name = "PROD_MGFR_ID")
	private ManufacturerMasterBean productManufacturerName;
	
	@Column(name = "PROD_HSN_CODE")
	private String hsnCode;
	
	@Column(name = "PROD_DELETION_FLAG")
	private int deletionFlag;
}
