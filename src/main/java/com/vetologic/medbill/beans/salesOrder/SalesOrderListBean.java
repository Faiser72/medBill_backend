package com.vetologic.medbill.beans.salesOrder;

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

import com.vetologic.medbill.beans.productCategoryMaster.ProductCategoryMasterBean;
import com.vetologic.medbill.beans.productMaster.ProductMasterBean;
import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "SALES_ORDER_LIST")
public class SalesOrderListBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SALES_ITM_ID")
	private int salesItemId;

	@ManyToOne
	@JoinColumn(name = "SALES_ID")
	private SalesOrderBean salesId;

	@Column(name = "SALES_ITM_DELETION_FLAG")
	private int deletionFlag;

	@ManyToOne
	@JoinColumn(name = "STOCK_ITEM_ID")
	private StockItemBean stockItemId;

	@Column(name = "SALES_ITM_QUANTITY")
	private int quantity;

	@Column(name = "SALES_ITM_TOTAL_AMT")
	private String amount;
}
