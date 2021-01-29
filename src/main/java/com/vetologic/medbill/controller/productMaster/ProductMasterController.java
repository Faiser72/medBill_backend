package com.vetologic.medbill.controller.productMaster;

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

import com.vetologic.medbill.beans.productMaster.ProductMasterBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.productMaster.ProductMasterService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("productMaster")
public class ProductMasterController {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);

	@Autowired
	private ProductMasterService productService;

	@PostMapping(path = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse saveProduct(@RequestBody ProductMasterBean productMasterBean,
			MedbillResponse medbillResponse) {
		productMasterBean.setDeletionFlag(0);
		productMasterBean.setCreatedDate(AppUtil.currentDateWithTime());
		int id = productService.save(productMasterBean);
		if (id != 0) {
			productMasterBean.setProductId(id);
			medbillResponse.setObject(productMasterBean);
			medbillResponse.setSuccess(true);
			medbillResponse.setMessage("Saved Sucessfully");
			log.info("Saved Successfully & Saved Product Id is: " + id);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Saved UnSucessfully");
			log.info("Saved UnSuccessfully");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/productList", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listAllProducts(MedbillResponse medbillResponse) {
		List<ProductMasterBean> productList = (List<ProductMasterBean>) productService.getAll("ProductMasterBean");
		if (productList.size() > 0) {
			System.out.println("productList" + productList);
			medbillResponse.setListObject(productList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product List is Empty");
			log.info("Product List is Empty");
		}
		return medbillResponse;
	}

	@GetMapping(path = "/getProductDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getProductDetails(@PathVariable int id, MedbillResponse medbillResponse) {
		ProductMasterBean manufacturer = (ProductMasterBean) productService.getById("ProductMasterBean", id);
		if (manufacturer != null) {
			medbillResponse.setObject(manufacturer);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product Not Exist");
			log.info("This Product Id: " + id + " Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/deleteProductDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse deleteProductDetails(@RequestParam("productId") int productId,
			MedbillResponse medbillResponse) {
		ProductMasterBean productCategory = (ProductMasterBean) productService.getById("ProductMasterBean", productId);
		if (productCategory != null) {
			productCategory.setDeletionFlag(1);
			if (productService.update(productCategory)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Deleted Successfully");
				log.info("This Product Id: " + productId + " Deleted Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Deletion Failed");
				log.info("Deletion Failed");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Product Not Exist");
			log.info("This Product Id: " + productId + " is Not Exist");
		}
		return medbillResponse;
	}

	@PutMapping(path = "/updateProductDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse updateProductDetails(@RequestBody ProductMasterBean product,
			MedbillResponse medbillResponse) {
		ProductMasterBean productDetails = (ProductMasterBean) productService.getById("ProductMasterBean",
				product.getProductId());
		if (productDetails != null) {
			productDetails.setProductCategory(product.getProductCategory());
			productDetails.setManufacturer(product.getManufacturer());
			productDetails.setHsnCode(product.getHsnCode());
			productDetails.setProductName(product.getProductName());
			productDetails.setUpdatedDate(AppUtil.currentDateWithTime());
			if (productService.update(productDetails)) {
				medbillResponse.setSuccess(true);
				medbillResponse.setMessage("Updated Successfully");
				log.info("This Product  Id: " + product.getProductId() + " Updated Successfully");
			} else {
				medbillResponse.setSuccess(false);
				medbillResponse.setMessage("Fails to Update");
				log.info("Fails to Update");
			}
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("This Product  Not Exist");
			log.info("This Product Id: " + product.getProductId() + " is Not Exist");
		}

		return medbillResponse;
	}

	// get list of data except this id for validate unique in (edit)
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getProductListExceptOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getProductListExceptOne(@PathVariable int id, MedbillResponse medbillResponse) {
		List<ProductMasterBean> productListExceptOne = (List<ProductMasterBean>) productService
				.getAllExceptOne("ProductMasterBean", id);
		if (productListExceptOne.size() > 0) {
			medbillResponse.setListObject(productListExceptOne);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Product List is Empty");
			log.info("Product List is Empty");
		}
		return medbillResponse;
	}

}
