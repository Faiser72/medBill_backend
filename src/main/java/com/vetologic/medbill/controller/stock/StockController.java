package com.vetologic.medbill.controller.stock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping(path = "/getStockDetailsById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getStockDetailsById(@PathVariable int id, MedbillResponse medbillResponse) {
		StockItemBean stock = (StockItemBean) stockService.getById("StockItemBean", id);
		if (stock != null) {
			medbillResponse.setObject(stock);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("stock Not Exist");
			log.info("This stock Id: " + id + " Not Exist");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getStockItemListBtStockId/{stockId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getStockItemListBtStockId(@PathVariable int stockId, MedbillResponse medbillResponse) {
		List<StockItemBean> stockItemList = (List<StockItemBean>) stockService
				.getStockItemListByStockId("StockItemBean", stockId);
		if (stockItemList.size() > 0) {
			System.out.println("StockItem" + stockItemList);
			medbillResponse.setListObject(stockItemList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("StockItem  is Empty");
			log.info("StockItem is Empty");
		}
		return medbillResponse;
	}

	// get list of data except this id for validate unique in (edit)
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getStockListExceptOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getStockListExceptOne(@PathVariable int id, MedbillResponse medbillResponse) {
		List<StockBean> StockListListExceptOne = (List<StockBean>) stockService.getAllExceptOne("StockBean", id);
		if (StockListListExceptOne.size() > 0) {
			medbillResponse.setListObject(StockListListExceptOne);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("Stock List is Empty");
			log.info("Stock List is Empty");
		}
		return medbillResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getStockItemListByProductId/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MedbillResponse getStockItemListByProductId(@PathVariable int productId, MedbillResponse medbillResponse) {
		List<StockItemBean> stockItemList = (List<StockItemBean>) stockService
				.getStockItemListByProductId("StockItemBean", productId);
		if (stockItemList.size() > 0) {
			System.out.println("StockItem" + stockItemList);
			medbillResponse.setListObject(stockItemList);
			medbillResponse.setSuccess(true);
		} else {
			medbillResponse.setSuccess(false);
			medbillResponse.setMessage("StockItem  is Empty");
			log.info("StockItem is Empty");
		}
		return medbillResponse;
	}
}
