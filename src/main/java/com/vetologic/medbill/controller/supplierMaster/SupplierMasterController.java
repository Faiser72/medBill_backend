package com.vetologic.medbill.controller.supplierMaster;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.beans.supplierMaster.SupplierMasterBean;
import com.vetologic.medbill.models.service.supplierMaster.SupplierMasterService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("supplierMaster")
public class SupplierMasterController {

	private static Logger log = LoggerFactory.getLogger(SupplierMasterController.class);

	@Autowired
	private SupplierMasterService supplierMasterService;

	@PostMapping(path = "/addSupplier", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse saveSupplier(@RequestBody SupplierMasterBean supplierMasterBean,
			MedbillResponse medbillResponse) {
		supplierMasterBean.setDeletionFlag(0);
		supplierMasterBean.setCreatedDate(AppUtil.currentDateWithTime());
		int id = supplierMasterService.save(supplierMasterBean);
		if (id != 0) {
			supplierMasterBean.setSupplierId(id);
			medbillResponse.setObject(supplierMasterBean);
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Saved Sucessfully");
			log.info("Saved Successfully & Saved Product Supplier Id is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSuccessfully");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/listSupplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listSupplier(MedbillResponse medbillResponse) {
		List<SupplierMasterBean> supplierList = (List<SupplierMasterBean>) supplierMasterService
				.getAll("SupplierMasterBean");
		if (supplierList.size() > 0) {
			medbillResponse.setListObject(supplierList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Supplier List is Empty");
			log.info("Supplier  List is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteSupplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteSupplierDetails(@RequestParam("supplierId") int supplierId,
			MedbillResponse medbillResponse) {
		SupplierMasterBean supplierDetails = (SupplierMasterBean) supplierMasterService.getById("SupplierMasterBean",
				supplierId);
		if (supplierDetails != null) {
			supplierDetails.setDeletionFlag(1);
			supplierDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			if (supplierMasterService.update(supplierDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Supplier Id: " + supplierId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Product Category Not Exist");
			log.info("This Supplier Id: " + supplierId + " is Not Exist");
		}
		return medbillResponse;
	}

	@GetMapping(path = "/getSupplierDetails/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getSupplierDetailById(@PathVariable int supplierId, MedbillResponse medbillResponse) {
		SupplierMasterBean supplierDetail = (SupplierMasterBean) supplierMasterService.getById("SupplierMasterBean",
				supplierId);
		if (supplierDetail != null) {
			medbillResponse.setObject(supplierDetail);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Supplier Not Exist");
			log.info("This Supplier Id: " + supplierId + " Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/updateSupplierDetail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse updateSupplierDetail(@RequestBody SupplierMasterBean supplierMasterBeanDetail,
			MedbillResponse medbillResponse) {
		SupplierMasterBean supplierMasterBean = (SupplierMasterBean) supplierMasterService.getById("SupplierMasterBean",
				supplierMasterBeanDetail.getSupplierId());
		if (supplierMasterBean != null) {
			supplierMasterBean.setSupplierName(supplierMasterBeanDetail.getSupplierName());
			supplierMasterBean.setContactNumber(supplierMasterBeanDetail.getContactNumber());
			supplierMasterBean.setAddress(supplierMasterBeanDetail.getAddress());
			supplierMasterBean.setContactPersonName(supplierMasterBeanDetail.getContactPersonName());
			supplierMasterBean.setContactPersonNumber(supplierMasterBeanDetail.getContactPersonNumber());
			supplierMasterBean.setContactPersonEmailId(supplierMasterBeanDetail.getContactPersonEmailId());
			supplierMasterBean.setUpdatedDate(AppUtil.currentDateWithTime());
			if (supplierMasterService.update(supplierMasterBean)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Updated Successfully");
				log.info("This Supplier Id: " + supplierMasterBean.getSupplierId() + " Updated Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Fails to Update");
				log.info("Fails to Update");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Supplier Not Exist");
			log.info("This Supplier Id: " + supplierMasterBeanDetail.getSupplierId() + " is Not Exist");
		}

		return medbillResponse;
	}
}
