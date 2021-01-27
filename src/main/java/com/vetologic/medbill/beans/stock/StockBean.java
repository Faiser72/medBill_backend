package com.vetologic.medbill.beans.stock;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.vetologic.medbill.beans.order.OrderBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "STOCK")
public class StockBean extends AbstractCreatedAndUpdated{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STOCK_ID")
	private int stockId;

	@ManyToOne
	@JoinColumn(name = "ORD_NUM")
	private OrderBean orderNumber;

	@Column(name = "STOCK_RECEIVED_DATE")
	private String receivedDate;

	@Column(name = "STOCK_SUPPLIER_INV_NUM")
	private String supplierInvoiceNumber;

	@Column(name = "STOCK_DELETION_FLAG")
	private int deletionFlag;

	@Column(name = "STOCK_GROSS_AMT")
	private String purchaseEntrySubTotal;

	@Column(name = "STOCK_DISCOUNT_AMT")
	private String purchaseEntryDiscount;

	@Column(name = "STOCK_TAX_AMT")
	private String purchaseEntryTax;

	@Column(name = "STOCK_TOTAL_NET_AMT")
	private String purchaseEntryTotal;

	@Transient
	private List<StockItemBean> stockList;

}
