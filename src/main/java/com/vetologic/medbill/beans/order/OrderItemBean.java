package com.vetologic.medbill.beans.order;

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
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItemBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORD_ITM_ID")
	private int orderItemId;
	
	@ManyToOne
	@JoinColumn(name = "ORD_ID")
	private OrderBean orderId;
	
	@ManyToOne
	@JoinColumn(name = "ORD_ITM_PROD_CAT_ID")
	private ProductCategoryMasterBean productType;
	
	@ManyToOne
	@JoinColumn(name = "ORD_ITM_PROD_ID")
	private ProductMasterBean productName;
	
	@Column(name = "ORD_ITM_PROD_PACKAGING")
	private String packaging;
	
	@Column(name = "ORD_ITM_PROD_MANUFACTURER")
	private String manufacturer;
	
	@Column(name = "ORD_ITM_PROD_QUANTITY")
	private String quantity;
	
	@Column(name = "ORD_ITM_PROD_UNIT_PRICE")
	private String unitPrice;
	
	@Column(name = "ORD_ITM_TOTAL_AMT")
	private String amount;
	
	@Column(name = "ORD_ITM_DELETION_FLAG")
	private int deletionFlag;
}
