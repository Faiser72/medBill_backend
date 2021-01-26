package com.vetologic.medbill.controller.purchaseEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryBean;
import com.vetologic.medbill.beans.purchaseEntry.PurchaseEntryItemBean;
import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.purchaseEntry.PurchaseEntryService;
import com.vetologic.medbill.utils.AppUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("purchaseEntry")
public class PurchaseEntryController {

private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);
	
	@Autowired
	private PurchaseEntryService purchaseEntryService;
	
//	@PostMapping(path = "/addPurchaseEntry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public MedbillResponse addPurchaseEntry(@RequestBody PurchaseEntryBean purchaseEntryBean, StockBean stockBean, MedbillResponse medbillResponse) {
//		purchaseEntryBean.setDeletionFlag(0);
//		purchaseEntryBean.setCreatedDate(AppUtil.currentDateWithTime());
//		int id = purchaseEntryService.save(purchaseEntryBean);
//		if (id != 0) {
//			purchaseEntryBean.setPurchaseEntryId(id);
//			for (PurchaseEntryItemBean purchaseItems : purchaseEntryBean.getPurchaseEntryList()) {
//				purchaseItems.setCreatedDate(AppUtil.currentDateWithTime());
//				purchaseItems.setPurchaseEntryId(purchaseEntryBean);
//				purchaseItems.setDeletionFlag(0);
//				purchaseEntryService.save(purchaseItems);
//			}
//			medbillResponse.setSuccess(true);
//			medbillResponse.setMessage("Saved Sucessfully");
//			log.info("Saved Sucessfully & Saved Purchase Id is: " + id);
//			stockBean.setDeletionFlag(0);
//			stockBean.setCreatedDate(AppUtil.currentDateWithTime());
//			int stockId= purchaseEntryService.save(stockBean);
//			
//			if(stockId !=0) {
//				stockBean.setStockId(id);
//				for (StockItemBean stockItems : stockBean.getStockList()) {
//					stockItems.setCreatedDate(AppUtil.currentDateWithTime());
//					stockItems.setStockId(stockBean);
//					stockItems.setDeletionFlag(0);
//					purchaseEntryService.save(stockItems);
//				}
//				medbillResponse.setSuccess(true);
//				medbillResponse.setMessage("Saved Sucessfully");
//				log.info("Saved Sucessfully & Saved Purchase Id is: " + id);
//			}
//			else {
//				medbillResponse.setSuccess(false);
//				medbillResponse.setMessage("Saved purchaseEntry, failed to save stock");
//				log.info("Saved purchaseEntry, failed to save stock");
//			}
//		} else {
//			medbillResponse.setSuccess(false);
//			medbillResponse.setMessage("Saved UnSucessfully");
//			log.info("Saved UnSucessfully");
//		}		
//		return medbillResponse;
//	}
}
