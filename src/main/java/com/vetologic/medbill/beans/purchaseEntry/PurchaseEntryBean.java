package com.vetologic.medbill.beans.purchaseEntry;

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
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "PURCHASE_ENTRY")
public class PurchaseEntryBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PUR_ENTRY_ID")
	private int purchaseEntryId;

	@ManyToOne
	@JoinColumn(name = "ORD_NUM")
	private OrderBean orderNumber;

	@Column(name = "PUR_RECEIVED_DATE")
	private String receivedDate;

	@Column(name = "PUR_SUPPLIER_INV_NUM")
	private String supplierInvoiceNumber;

	@Column(name = "PUR_DELETION_FLAG")
	private int deletionFlag;

	@Column(name = "PUR_GROSS_AMT")
	private String purchaseEntrySubTotal;

	@Column(name = "PUR_DISCOUNT_AMT")
	private String purchaseEntryDiscount;

	@Column(name = "PUR_TAX_AMT")
	private String purchaseEntryTax;

	@Column(name = "PUR_TOTAL_NET_AMT")
	private String purchaseEntryTotal;

	@Transient
	private List<PurchaseEntryItemBean> purchaseEntryList;

	@Transient
	private List<StockItemBean> stockList;

}
