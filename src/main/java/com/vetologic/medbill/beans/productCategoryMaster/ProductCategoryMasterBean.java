package com.vetologic.medbill.beans.productCategoryMaster;

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
@Table(name = "PRODUCT_CATEGORY_MASTER")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductCategoryMasterBean extends AbstractCreatedAndUpdated{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROD_CAT_ID")
	private int categoryId;
	
	@Column(name = "PROD_CAT_NAME")
	private String categoryName;
	
	@Column(name = "PROD_CAT_DESCRIPTION")
	private String categoryDescription;
	
	@Column(name = "PROD_CAT_DELETION_FLAG")
	private int deletionFlag;
}
