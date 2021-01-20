package com.vetologic.medbill.controller.productCategoryMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

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
			log.info("Saved Successfully & Saved Product Category Id is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSuccessfully");
		}
		return medbillResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/productCategoryList", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listAllProductCategory(MedbillResponse medbillResponse) {
		List<ProductCategoryMasterBean> productCategoryList = (List<ProductCategoryMasterBean>) productCategoryService
				.getAll("ProductCategoryMasterBean");
		if (productCategoryList.size() > 0) {
			System.out.println("productCategoryList" + productCategoryList);
			medbillResponse.setListObject(productCategoryList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product Category List is Empty");
			log.info("Product Category List is Empty");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteProductCategoryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteProductCategoryDetails(@RequestParam("categoryId") int categoryId,
			MedbillResponse medbillResponse) {
		ProductCategoryMasterBean productCategory = (ProductCategoryMasterBean) productCategoryService.getById("ProductCategoryMasterBean",
				categoryId);
		if (productCategory != null) {
			productCategory.setDeletionFlag(1);
			if (productCategoryService.update(productCategory)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Product Category Id: " + categoryId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Product Category Not Exist");
			log.info("This Product Category Id: " + categoryId + " is Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/updateProductCategoryDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse updateProductCategoryDetails(@RequestBody ProductCategoryMasterBean productCategory, MedbillResponse medbillResponse) {
		ProductCategoryMasterBean productCategoryDetails = (ProductCategoryMasterBean) productCategoryService
				.getById("ProductCategoryMasterBean", productCategory.getCategoryId());
		if (productCategoryDetails != null) {
			productCategoryDetails.setCategoryDescription(productCategory.getCategoryDescription());
			productCategoryDetails.setCategoryName(productCategory.getCategoryName());
			productCategoryDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			if (productCategoryService.update(productCategoryDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Updated Successfully");
				log.info("This Product Categogy Id: " + productCategory.getCategoryId() + " Updated Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Fails to Update");
				log.info("Fails to Update");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Product Categogy Not Exist");
			log.info("This Product Categogy Id: " + productCategory.getCategoryId() + " is Not Exist");
		}

		return medbillResponse;
	}

	// get list of data except this id for validate unique in (edit)
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getProductCategoryListExceptOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getProductCategoryListExceptOne(@PathVariable int id, MedbillResponse medbillResponse) {
		List<ProductCategoryMasterBean> productCategoryListExceptOne = (List<ProductCategoryMasterBean>) productCategoryService
				.getAllExceptOne("ProductCategoryMasterBean", id);
		if (productCategoryListExceptOne.size() > 0) {
			medbillResponse.setListObject(productCategoryListExceptOne);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product Categogy List is Empty");
			log.info("Product Categogy List is Empty");
		}
		return medbillResponse;
	}

}
