package com.vetologic.medbill.controller.productCategoryMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.productCategoryMaster.ProductCategoryMasterBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.models.service.productCategoryMaster.ProductCategoryService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("productCategotyMaster")
public class ProductCategoryMasterController {

private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@PostMapping(path = "/addProductCategory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse saveItemCategory(@RequestBody ProductCategoryMasterBean productCategoryMasterBean, MedbillResponse medbillResponse) {
		productCategoryMasterBean.setDeletionFlag(0);
		productCategoryMasterBean.setCreatedDate(AppUtil.currentDateWithTime());
		int id = productCategoryService.save(productCategoryMasterBean);
		if (id != 0) {
			productCategoryMasterBean.setCategoryId(id);
			medbillResponse.setObject(productCategoryMasterBean);
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Saved Sucessfully");
			log.info("Saved Sucessfully & Saved Product Category Id is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSucessfully");
		}
		return medbillResponse;
	}
}
