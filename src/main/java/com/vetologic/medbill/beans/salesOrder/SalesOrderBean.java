package com.vetologic.medbill.beans.salesOrder;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "SALES_LIST")
public class SalesOrderBean extends AbstractCreatedAndUpdated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SALES_ID")
	private int salesOrderId;
	
	@Column(name = "SALES_CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "SALES_INV_NUM")
	private String invoiceNumber;
	
	@Column(name = "SALES_DOCTOR_NAME")
	private String doctorName;
	
	@Column(name = "SALES_DATE")
	private String salesDate;
	
	@Column(name = "SALES_SUB_TOTAL_AMT")
	private String subTotal;
	
	@Column(name = "SALES_PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name = "SALES_SGST_AMT")
	private String sgstAmount;
	
	@Column(name = "SALES_ORD_CANCELLATION_FLAG")
	private int cancellationFlag;
	
	@Column(name = "SALES_CGST_AMT")
	private String cgstAmount;
	
	@Column(name = "SALES_TOTAL_NET_AMT")
	private String totalNetAmount;
	
	@Column(name = "SALES_DELETION_FLAG")
	private int deletionFlag;
	
	@Transient
	private List<SalesOrderListBean> salesOrderList;
}
