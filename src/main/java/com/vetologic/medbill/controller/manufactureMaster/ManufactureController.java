package com.vetologic.medbill.controller.manufactureMaster;

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

import com.vetologic.medbill.beans.manufacturerMaster.ManufacturerMasterBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.manufactureMaster.ManufactureService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("manufactureMaster")
public class ManufactureController {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);

	@Autowired
	private ManufactureService manufactureService;

	@PostMapping(path = "/addManufacture", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse saveManufacture(@RequestBody ManufacturerMasterBean manufacturerMasterBean,
			MedbillResponse medbillResponse) {
		manufacturerMasterBean.setDeletionFlag(0);
		manufacturerMasterBean.setCreatedDate(AppUtil.currentDateWithTime());
		int id = manufactureService.save(manufacturerMasterBean);
		if (id != 0) {
			manufacturerMasterBean.setManufacturerId(id);
			medbillResponse.setObject(manufacturerMasterBean);
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Saved Sucessfully");
			log.info("Saved Successfully & Saved Manufacture Id is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSuccessfully");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/manufactureList", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listAllManufacture(MedbillResponse medbillResponse) {
		List<ManufacturerMasterBean> manufactureList = (List<ManufacturerMasterBean>) manufactureService
				.getAll("ManufacturerMasterBean");
		if (manufactureList.size() > 0) {
			System.out.println("productList" + manufactureList);
			medbillResponse.setListObject(manufactureList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("manufacture List is Empty");
			log.info("manufacture List is Empty");
		}
		return medbillResponse;
	}

	@GetMapping(path = "/getManufacturerDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getmanufacturerDetailsById(@PathVariable int id, MedbillResponse medbillResponse) {
		ManufacturerMasterBean manufacturer = (ManufacturerMasterBean) manufactureService
				.getById("ManufacturerMasterBean", id);
		if (manufacturer != null) {
			medbillResponse.setObject(manufacturer);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("manufacturer Not Exist");
			log.info("This manufacturer Id: " + id + " Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteManufactureDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteManufactureDetails(@RequestParam("manufacturerId") int manufacturerId,
			MedbillResponse medbillResponse) {
		ManufacturerMasterBean manufacture = (ManufacturerMasterBean) manufactureService
				.getById("ManufacturerMasterBean", manufacturerId);
		if (manufacture != null) {
			manufacture.setDeletionFlag(1);
			if (manufactureService.update(manufacture)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This manufacture Id: " + manufacturerId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This manufacture Not Exist");
			log.info("This manufacture Id: " + manufacturerId + " is Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/updateManufactureDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse updateManufactureDetails(@RequestBody ManufacturerMasterBean manufacture,
			MedbillResponse medbillResponse) {
		ManufacturerMasterBean manufactureDetails = (ManufacturerMasterBean) manufactureService
				.getById("ManufacturerMasterBean", manufacture.getManufacturerId());
		if (manufactureDetails != null) {
			manufactureDetails.setAddress(manufacture.getAddress());
			manufactureDetails.setContactNumber(manufacture.getContactNumber());
			manufactureDetails.setContactPersonEmailId(manufacture.getContactPersonEmailId());
			manufactureDetails.setContactPersonName(manufacture.getContactPersonName());
			manufactureDetails.setContactPersonNumber(manufacture.getContactPersonNumber());
			manufactureDetails.setManufacturerName(manufacture.getManufacturerName());
			manufactureDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			if (manufactureService.update(manufactureDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Updated Successfully");
				log.info("This manufacture  Id: " + manufacture.getManufacturerId() + " Updated Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Fails to Update");
				log.info("Fails to Update");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This manufacture  Not Exist");
			log.info("This manufacture Id: " + manufacture.getManufacturerId() + " is Not Exist");
		}

		return medbillResponse;
	}

	// get list of data except this id for validate unique in (edit)
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getManufactureListExceptOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getManufactureListExceptOne(@PathVariable int id, MedbillResponse medbillResponse) {
		List<ManufacturerMasterBean> manufactureListExceptOne = (List<ManufacturerMasterBean>) manufactureService
				.getAllExceptOne("ManufacturerMasterBean", id);
		if (manufactureListExceptOne.size() > 0) {
			medbillResponse.setListObject(manufactureListExceptOne);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Manufacture List is Empty");
			log.info("Manufacture List is Empty");
		}
		return medbillResponse;
	}

}
