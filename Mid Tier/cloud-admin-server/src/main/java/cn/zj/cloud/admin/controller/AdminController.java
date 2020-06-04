package cn.zj.cloud.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import cn.zj.cloud.admin.entity.Company;
import cn.zj.cloud.admin.entity.IpoDetail;
import cn.zj.cloud.admin.entity.Sector;
import cn.zj.cloud.admin.entity.StockExchange;
import cn.zj.cloud.admin.entity.StockPrice;
import cn.zj.cloud.admin.model.DeleteStockExchangeRequest;
import cn.zj.cloud.admin.model.StockPriceInfoRequest;
import cn.zj.cloud.admin.model.UpdateCompanyStatusRequest;
import cn.zj.cloud.admin.service.AdminService;
import cn.zj.cloud.constant.Constant;
import cn.zj.cloud.model.Response;
import cn.zj.cloud.model.StockPriceRowModel;
import cn.zj.cloud.util.ExcelHandler;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	// Company related process
	// Query all company info
	@GetMapping("/company")
	public ResponseEntity<Response> queryCompany() {
		Response response = adminService.queryCompaniesInfo();
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Query all company info which matched specified kewword
	@GetMapping("/company/ajax/{keyword}")
	public ResponseEntity<Response> queryCompanyByKeyWord(@PathVariable String keyword) {
		Response response = adminService.queryCompanyByKeyWord(keyword);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Query a company info which matched specified company code
	@GetMapping("/company/{companyCode}")
	public ResponseEntity<Response> queryCompanyByCompanyCode(@PathVariable String companyCode) {
		Response response = adminService.queryCompanyByCompanyCode(companyCode);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Regist company info
	@PostMapping("/company")
	public ResponseEntity<Response> registCompany(@RequestBody Company company) {
		Response response = adminService.registCompany(company);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Update company status
	@PutMapping("/company")
	public ResponseEntity<Response> updateCompanyStatus(@RequestBody UpdateCompanyStatusRequest requestBody) {
		String id = requestBody.getId();
		String status = requestBody.getStatus();
		Response response = adminService.updateCompanyStatus(id, status);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// IpoDetail related process
	// Get IPOs Data which matched company name
	@GetMapping("/ipodetail")
	public ResponseEntity<Response> queryIpoDetails() {
		Response response = adminService.queryIpoDetails();
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Get All IPOs Data
	@GetMapping("/ipodetail/{companyname}")
	public ResponseEntity<Response> queryIpoDetailsByCompanyName(@PathVariable String companyname) {
		Response response = adminService.queryIpoDetailsByCompanyName(companyname);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Register IpoDetail
	@PostMapping("/ipodetail")
	public ResponseEntity<Response> registIpoDetail(@RequestBody IpoDetail ipodetail) {
		Response response = adminService.registIpoDetail(ipodetail);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// Update IpoDetail
	@PutMapping("/ipodetail")
	public ResponseEntity<Response> updateIpoDetail(@RequestBody IpoDetail ipodetail) {
		Response response = adminService.updateIpoDetail(ipodetail);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	// Sector related process
	@GetMapping("/sector")
	public ResponseEntity<Response> querySector() {
		Response response = adminService.querySector();
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("/sector/{id}")
	public ResponseEntity<Response> querySectorById(@PathVariable String id) {
		Response response = adminService.querySectorById(id);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping("/sector")
	public ResponseEntity<Response> registSector(@RequestBody Sector sector) {
		Response response = adminService.registSector(sector);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	// StockExchange related process
	@GetMapping("/stockexchange")
	public ResponseEntity<Response> queryStockExchange() {
		Response response = adminService.queryStockExchange();
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping("/stockexchange")
	public ResponseEntity<Response> registStockExchange(@RequestBody StockExchange stockExchange) {
		Response response = adminService.registStockExchange(stockExchange);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping("/stockexchange/del")
	public ResponseEntity<Response> delStockExchangeById(@RequestBody List<DeleteStockExchangeRequest> delStockExList) {
		Response response = adminService.deleteStockExchangeById(delStockExList);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("/stockprice")
	public ResponseEntity<Response> getStockPriceInfo(@RequestBody StockPriceInfoRequest request){
		Response response = adminService.getStockPriceInfo(request);
		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);

	}

//	// Stock Price related process
//	@PostMapping("/stockprice")
//	public ResponseEntity<Response> registStockExchange(@RequestBody List<StockPrice> StockPrice) {
//		Response response = adminService.uploadStockPrice(StockPrice);
//		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
//	}

	// Download stock price template
	@GetMapping("/template/download")
	public void downloadTemplate(HttpServletResponse response) {

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			File file = ResourceUtils.getFile("classpath:templates/temp.xls");
			if (file.exists()) {
				String fileName = file.getName();
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding("utf-8");
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
				byte[] buffer = new byte[1024];

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@PostMapping("/upload")
	public ResponseEntity<Response> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
		Response response = new Response();
		if (null == uploadFile) {
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(Constant.UPLOAD_FILE_FAIL);
		}
		String fileName = uploadFile.getOriginalFilename().toLowerCase();
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")){
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(Constant.UPLOAD_FILE_FAIL);
		}

		try {
			// Get stock price list from excel
			List<StockPriceRowModel> stockPriceRowModelList = ExcelHandler.getStockPriceListFromExcel(uploadFile.getInputStream());
			// convert to table entity
			List<StockPrice> stockPriceList = new ArrayList<StockPrice>();
			for(StockPriceRowModel model: stockPriceRowModelList) {
				StockPrice stockPrice = new StockPrice();
				BeanUtils.copyProperties(model, stockPrice);
				stockPriceList.add(stockPrice);
			}
			response = adminService.uploadStockPrice(stockPriceList);
			
		} catch (IOException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.status(response.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
		
	}
	
}
