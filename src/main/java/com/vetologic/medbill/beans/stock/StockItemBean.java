package com.vetologic.medbill.beans.stock;

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
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryItemBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "STOCK_ITEM")
public class StockItemBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STOCK_ITM_ID")
	private int stockItemId;

	@ManyToOne
	@JoinColumn(name = "STOCK_ID")
	private StockBean stockId;

	@Column(name = "STOCK_ITM_DELETION_FLAG")
	private int deletionFlag;

	@Column(name = "STOCK_ITM_PROD_PACKAGING")
	private String packaging;

	@Column(name = "STOCK_ITM_PROD_QUANTITY")
	private String quantity;

	@Column(name = "STOCK_ITM_PROD_UNIT_PRICE")
	private String unitPrice;

	@ManyToOne
	@JoinColumn(name = "STOCK_ITM_PROD_TYPE")
	private ProductCategoryMasterBean productType;

	@ManyToOne
	@JoinColumn(name = "STOCK_ITM_PROD_NAME")
	private ProductMasterBean productName;

	@Column(name = "STOCK_ITM_MANUFACTURER")
	private String manufacturer;

	@Column(name = "STOCK_ITM_BATCH_NUMBER")
	private String batchNumber;

	@Column(name = "STOCK_ITM_MGFR_DATE")
	private String manufactureDate;

	@Column(name = "STOCK_ITM_EXP_DATE")
	private String expiryDate;

	@Column(name = "STOCK_ITM_TOTAL_AMT")
	private String amount;

	@ManyToOne
	@JoinColumn(name = "PURCHASE_ITEM_ID")
	private PurchaseEntryItemBean purcItemBean;

	@Column(name = "STOCK_ITM_RETURN_FLAG")
	private boolean returnFlag;

}
