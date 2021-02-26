package com.vetologic.medbill.beans.order;

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

import com.vetologic.medbill.beans.supplierMaster.SupplierMasterBean;
import com.vetologic.medbill.utils.AbstractCreatedAndUpdated;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "CREATE_ORDER")
public class OrderBean extends AbstractCreatedAndUpdated {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORD_ID")
	private int orderId;
	
	@Column(name = "ORD_NUM")
	private String orderNumber;
	
	@Column(name = "ORD_DATE")
	private String orderDate;
	
	@ManyToOne
	@JoinColumn(name = "ORD_SUP_ID")
	private SupplierMasterBean supplierName;
	
	@Column(name = "ORD_NET_TOTAL_AMT")
	private String orderGrandTotal;
	
	@Column(name = "ORD_DELETION_FLAG")
	private int deletionFlag;
	
	@Transient
	List<OrderItemBean> orderItemList;
	
	@Column(name = "ORD_CANCELLATION_FLAG")
	private int cancellationFlag;
}
