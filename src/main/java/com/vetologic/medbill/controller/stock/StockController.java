package com.vetologic.medbill.controller.stock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.medbill.beans.response.MedbillResponse;
import com.vetologic.medbill.beans.stock.StockBean;
import com.vetologic.medbill.beans.stock.StockItemBean;
import com.vetologic.medbill.controller.productCategoryMaster.ProductCategoryMasterController;
import com.vetologic.medbill.models.service.stock.StockService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("stock")
public class StockController {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryMasterController.class);

	@Autowired
	private StockService stockService;

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/listStock", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listStock(MedbillResponse medbillResponse) {
		List<StockBean> stockList = (List<StockBean>) stockService.getAll("StockBean");
		if (stockList.size() > 0) {
			medbillResponse.setListObject(stockList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("stockList  is Empty");
			log.info("stockList  is Empty");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/listAllStockItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse listAllStockItems(MedbillResponse medbillResponse) {
		List<StockItemBean> stockItemList = (List<StockItemBean>) stockService.getAll("StockItemBean");
		if (stockItemList.size() > 0) {
			medbillResponse.setListObject(stockItemList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("stockItemList  is Empty");
			log.info("stockItemList  is Empty");
		}
		return medbillResponse;
	}
}
