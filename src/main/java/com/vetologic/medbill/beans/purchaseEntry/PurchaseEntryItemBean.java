package com.vetologic.medbill.beans.purchaseEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vetologic.medbill.beans.productCategoryMaster.ProductCategoryMasterBean;
import com.vetologic.medbill.beans.productMaster.ProductMasterBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "PURCHASE_ENTRY_ITEM")
public class PurchaseEntryItemBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PUR_ENTRY_ITM_ID")
	private int purchaseEntryItemId;

	@ManyToOne
	@JoinColumn(name = "PUR_ENTRY_ID")
	private PurchaseEntryBean purchaseEntryId;

	@Column(name = "PUR_ITM_DELETION_FLAG")
	private int deletionFlag;

	@Column(name = "PUR_ITM_PROD_PACKAGING")
	private String packaging;

	@Column(name = "PUR_ITM_PROD_QUANTITY")
	private String quantity;

	@Column(name = "PUR_ITM_PROD_UNIT_PRICE")
	private String unitPrice;

	@ManyToOne
	@JoinColumn(name = "PUR_ITM_PROD_TYPE")
	private ProductCategoryMasterBean productType;

	@ManyToOne
	@JoinColumn(name = "PUR_ITM_PROD_NAME")
	private ProductMasterBean productName;

	@Column(name = "PUR_ITM_MANUFACTURER")
	private String manufacturer;

	@Column(name = "PUR_ITM_BATCH_NUMBER")
	private String batchNumber;

	@Column(name = "PUR_ITM_MGFR_DATE")
	private String manufactureDate;

	@Column(name = "PUR_ITM_EXP_DATE")
	private String expiryDate;

	@Column(name = "PUR_ITM_TOTAL_AMT")
	private String amount;
}
