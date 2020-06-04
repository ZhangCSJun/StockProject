package cn.zj.cloud.admin.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cn.zj.cloud.admin.entity.Company;
import cn.zj.cloud.admin.entity.IpoDetail;
import cn.zj.cloud.admin.entity.Sector;
import cn.zj.cloud.admin.entity.StockExchange;
import cn.zj.cloud.admin.entity.StockPrice;

import cn.zj.cloud.admin.model.DeleteStockExchangeRequest;
import cn.zj.cloud.admin.model.StockPriceInfo;
import cn.zj.cloud.admin.model.StockPriceInfoRequest;
import cn.zj.cloud.admin.repository.AdminRepository;
import cn.zj.cloud.admin.repository.CompanyRepository;
import cn.zj.cloud.admin.repository.IpodetailRepository;
import cn.zj.cloud.admin.repository.SectorRepository;
import cn.zj.cloud.admin.repository.StockExchangeRepository;
import cn.zj.cloud.admin.repository.StockPriceRepository;
import cn.zj.cloud.admin.util.Util;
import cn.zj.cloud.constant.Constant;
import cn.zj.cloud.enums.TableName;
import cn.zj.cloud.model.Response;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Service
public class AdminService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private IpodetailRepository ipodetailRepository;
	@Autowired
	private StockExchangeRepository stockExchangeRepository;
	@Autowired
	private StockPriceRepository stockPriceRepository;
	@Autowired
	private SectorRepository sectorRepository;

	/**
	 * Register basic infomation about company
	 * 
	 * @param company
	 * @return
	 */
	public Response registCompany(Company company) {
		Response response = new Response();
		// generate id
		String id = generateId(TableName.COMPANY);
		company.setId(id);

		try {
			Company newCompany = companyRepository.save(company);
			String companyId = newCompany.getId();
			if (!"".equals(companyId)) {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ONE);
				response.setMessage(Constant.REGIST_SUCESS);

			} else {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ZERO);
				response.setMessage(Constant.REGIST_FAIL);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	/**
	 * Update Company Status
	 * 
	 * @param id     String
	 * @param status String
	 * @return
	 */
	public Response updateCompanyStatus(String id, String status) {
		Response response = new Response();
		int updateRow = companyRepository.updateCompanyStatus(id, status);

		if (updateRow == 1) {
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(Constant.UPDATE_STATUS_SUCESS);

		} else {
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(Constant.UPDATE_STATUS_FAIL);
		}
		return response;
	}

	/**
	 * Query All Companies' Info
	 * 
	 * @param companyName String
	 * @return
	 */
	public Response queryCompaniesInfo() {
		Response response = new Response();
		List<Company> companies = companyRepository.findAll();
		;
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (companies.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(companies.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, companies);
		response.setBusiness(business);

		return response;
	}

	/**
	 * Query company info by keyword
	 * 
	 * @param companyName String
	 * @return
	 */
	public Response queryCompanyByKeyWord(String keyWord) {
		Response response = new Response();
		List<Company> companies = companyRepository.queryCompanyByKeyWord(keyWord);
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (companies.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(companies.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, companies);
		response.setBusiness(business);

		return response;
	}

	/**
	 * Query company info by company code
	 * 
	 * @param companyCode String
	 * @return
	 */
	public Response queryCompanyByCompanyCode(String companyCode) {
		Response response = new Response();
		List<Company> companies = companyRepository.queryByCompanyCode(companyCode);
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (companies.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(companies.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, companies);
		response.setBusiness(business);

		return response;
	}

	/**
	 * IpoDetail Regist
	 * 
	 * @param ipoDetail
	 * @return
	 */
	public Response registIpoDetail(IpoDetail ipoDetail) {
		Response response = new Response();
		// generate id
		String id = generateId(TableName.IPODETAIL);
		ipoDetail.setId(id);

		try {
			IpoDetail newIpoDetail = ipodetailRepository.save(ipoDetail);
			String ipoDetailId = newIpoDetail.getId();
			if (!"".equals(ipoDetailId)) {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ONE);
				response.setMessage(Constant.REGIST_SUCESS);

			} else {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ZERO);
				response.setMessage(Constant.REGIST_FAIL);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	/**
	 * IpoDetail Update
	 * 
	 * @param ipoDetail
	 * @return
	 */
	public Response updateIpoDetail(IpoDetail ipoDetail) {
		Response response = new Response();

		try {
			ipodetailRepository.save(ipoDetail);
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(Constant.UPDATE_SUCESS);
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	/**
	 * Query IpoDetails
	 * 
	 * @return
	 */
	public Response queryIpoDetails() {
		Response response = new Response();
		List<IpoDetail> ipoDetails = ipodetailRepository.findAll();
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (ipoDetails.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(ipoDetails.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, ipoDetails);
		response.setBusiness(business);

		return response;
	}

	/**
	 * Query IpoDetails By Company Name
	 * 
	 * @param companyName String
	 * @return
	 */
	public Response queryIpoDetailsByCompanyName(String companyName) {
		Response response = new Response();
		List<IpoDetail> ipoDetails = ipodetailRepository.queryByCompanyName(companyName);
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (ipoDetails.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(ipoDetails.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, ipoDetails);
		response.setBusiness(business);

		return response;
	}

	/**
	 * Sector Regist
	 * 
	 * @param sector
	 * @return
	 */
	public Response registSector(Sector sector) {
		Response response = new Response();
		// generate id
		String id = generateId(TableName.SECTOR);
		sector.setId(id);

		try {
			Sector newSector = sectorRepository.save(sector);
			String sectorId = newSector.getId();
			if (!"".equals(sectorId)) {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ONE);
				response.setMessage(Constant.REGIST_SUCESS);

			} else {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ZERO);
				response.setMessage(Constant.REGIST_FAIL);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * Query All Sectors' Info
	 * 
	 * @param companyName String
	 * @return
	 */
	public Response querySector() {
		Response response = new Response();
		List<Sector> sectors = sectorRepository.findAll();

		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (sectors.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(sectors.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, sectors);
		response.setBusiness(business);

		return response;
	}

	/**
	 * Query Sector by id
	 * 
	 * @param id String
	 * @return
	 */
	public Response querySectorById(String id) {
		Response response = new Response();
		Optional<Sector> optinal = sectorRepository.findById(id);
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (optinal.isPresent()) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", Constant.NUM_ONE));
			Map<String, Object> business = new LinkedHashMap<String, Object>();
			business.put(Constant.BUSINESS_DATA_DATA, optinal.get());
			response.setBusiness(business);
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}

		return response;
	}

	/**
	 * StockExchange Regist
	 * 
	 * @param stockExchange StockExchange
	 * @return
	 */
	public Response registStockExchange(StockExchange stockExchange) {
		Response response = new Response();
		// generate id
		String id = generateId(TableName.STOCKEXCHANGE);
		stockExchange.setId(id);

		try {
			StockExchange newStockExchange = stockExchangeRepository.save(stockExchange);
			String stockExchangeId = newStockExchange.getId();
			if (!"".equals(stockExchangeId)) {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ONE);
				response.setMessage(Constant.REGIST_SUCESS);

			} else {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ZERO);
				response.setMessage(Constant.REGIST_FAIL);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * Query All StockExchanges' Info
	 * 
	 * @return
	 */
	public Response queryStockExchange() {
		Response response = new Response();
		List<StockExchange> stockExchangeList = stockExchangeRepository.findAll();

		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		if (stockExchangeList.size() > 0) {
			response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(stockExchangeList.size())));
		} else {
			response.setMessage(Constant.QUERY_DATA_FAIL);
		}
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, stockExchangeList);
		response.setBusiness(business);

		return response;
	}

	/**
	 * StockExchange Delete
	 * 
	 * @param id String
	 * @return
	 */
	public Response deleteStockExchangeById(List<DeleteStockExchangeRequest> delStockExList) {
		Response response = new Response();

		int totalNum = delStockExList.size();
		int successNum = 0;
		int errorNum = 0;
		for (DeleteStockExchangeRequest delStockEx : delStockExList) {
			String id = delStockEx.getId();
			try {
				stockExchangeRepository.deleteById(id);
				successNum += 1;
			} catch (Exception e) {
				errorNum += 1;
			}
		}
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		response.setMessage(Constant.DELETE_DATA_NUM.replace("{0}", String.valueOf(totalNum))
				.replace("{1}", String.valueOf(successNum)).replace("{2}", String.valueOf(errorNum)));

		return response;
	}

	/**
	 * Get comparison charts data
	 * 
	 * @param companyCode List<String>
	 * @return
	 */
	public Response getStockPriceInfo(StockPriceInfoRequest request) {
		List<String> companyCode = request.getCompanyCode();
		String year = request.getYear();
		String quater = request.getQuater();
		List<String> yearMonthList = new ArrayList<String>();
		switch (quater) {
		case Constant.Q1:
			yearMonthList.add(year + "/01");
			yearMonthList.add(year + "/02");
			yearMonthList.add(year + "/03");
			break;
		case Constant.Q2:
			yearMonthList.add(year + "/04");
			yearMonthList.add(year + "/05");
			yearMonthList.add(year + "/06");
			break;
		case Constant.Q3:
			yearMonthList.add(year + "/07");
			yearMonthList.add(year + "/08");
			yearMonthList.add(year + "/09");
			break;
		case Constant.Q4:
			yearMonthList.add(year + "/10");
			yearMonthList.add(year + "/11");
			yearMonthList.add(year + "/12");
			break;
		}

		Map<String,List<StockPriceInfo>> data = new HashMap<String,List<StockPriceInfo>>();
		int searchedCount = 0;
		for(String yearMonth: yearMonthList) {
			List<StockPriceInfo> stockPriceInfoList= stockPriceRepository.queryStockPriceInfo(companyCode, yearMonth);

			if(stockPriceInfoList.size()>0) {
				searchedCount += stockPriceInfoList.size();
				String strMonth = yearMonth.substring(yearMonth.lastIndexOf("/")+1);
				data.put(strMonth, stockPriceInfoList);
			}
		}

		Response response = new Response();
		response.setStatus(HttpStatus.OK.value());
		response.setCode(Constant.CODE_ONE);
		response.setMessage(Constant.QUERY_DATA_SUCESS.replace("{0}", String.valueOf(searchedCount)));
		Map<String, Object> business = new LinkedHashMap<String, Object>();
		business.put(Constant.BUSINESS_DATA_DATA, data);
		response.setBusiness(business);
		return response;
	}

	/**
	 * upload Stock Price
	 * 
	 * @param stockPriceList List<StockPrice> @return`
	 */
	public Response uploadStockPrice(List<StockPrice> stockPriceList) {
		Response response = new Response();

		int uploadTotalNum = stockPriceList.size();
		StringBuilder dateBuild = new StringBuilder();

		StockPrice firstOne = stockPriceList.get(0);

		String companyCode = firstOne.getCompanycode();
		String dateFrom_date = firstOne.getDate();
		String dateFrom_time = firstOne.getTime();
		String companyName = Constant.EMPTY_STRING;
		String stockExchange = Constant.EMPTY_STRING;
		Optional<StockPrice> opStockPrice = stockPriceRepository.queryIsPresent(companyCode, dateFrom_date,
				dateFrom_time);
		// check if data is latest
		if (opStockPrice.isPresent()) {
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ZERO);
			response.setMessage(Constant.UPLOAD_FILE_FAIL_INVALID_DATE_RANGE);
		} else {
			// get company name which matched company code
			List<Company> companyList = companyRepository.queryByCompanyCode(companyCode);
			if (companyList.size() == 1) {
				companyName = companyList.get(0).getName();
			} else if (companyList.size() == 0) {
				response.setStatus(HttpStatus.OK.value());
				response.setCode(Constant.CODE_ZERO);
				response.setMessage(Constant.UPLOAD_FILE_FAIL_INVALID_COMPANY_CODE);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setCode(Constant.CODE_ZERO);
				response.setMessage(Constant.MESSAGE_INTERNAL_SERVER_ERROR);
			}

			// get stock exchange
			stockExchange = firstOne.getStockexchange();
			// first one
			String dateFrom = dateBuild.append(dateFrom_date).append(" ").append(dateFrom_time).toString();
			// last one
			dateBuild = new StringBuilder();
			StockPrice lastOne = stockPriceList.get(uploadTotalNum - 1);
			String dateTo = dateBuild.append(lastOne.getDate()).append(" ").append(lastOne.getTime()).toString();

			// save data
			stockPriceRepository.saveAll(stockPriceList);

			// create response
			response.setStatus(HttpStatus.OK.value());
			response.setCode(Constant.CODE_ONE);
			response.setMessage(Constant.UPLOAD_FILE_SUCESS);
			Map<String, Object> dataContent = new LinkedHashMap<String, Object>();
			dataContent.put("company", companyName);
			dataContent.put("stockExchange", stockExchange);
			dataContent.put("totalnum", String.valueOf(uploadTotalNum));
			dataContent.put("datefrom", dateFrom);
			dataContent.put("dateto", dateTo);

			Map<String, Object> business = new LinkedHashMap<String, Object>();
			business.put(Constant.BUSINESS_DATA_DATA, dataContent);
			response.setBusiness(business);
		}
		return response;
	}

	/**
	 * Generate table id
	 * 
	 * @param tablename TableName
	 * @return`next id
	 */
	private String generateId(TableName tablename) {
		String currentId = Constant.EMPTY_STRING;
		String nextId = Constant.EMPTY_STRING;
		List<?> queryList = null;
		switch (tablename) {
		case COMPANY:
			queryList = companyRepository.queryMaxId();
			if (queryList.size() == 1) {
				currentId = ((Company) queryList.get(0)).getId();
			} else {
				nextId = "C10000101";
			}
			;
			break;
		case IPODETAIL:
			queryList = ipodetailRepository.queryMaxId();
			if (queryList.size() == 1) {
				currentId = ((IpoDetail) queryList.get(0)).getId();
			} else {
				nextId = "I10000101";
			}
			;
			break;
		case SECTOR:
			queryList = sectorRepository.queryMaxId();
			if (queryList.size() == 1) {
				currentId = ((Sector) queryList.get(0)).getId();
			} else {
				nextId = "S10101";
			}
			;
			break;
		case STOCKEXCHANGE:
			queryList = stockExchangeRepository.queryMaxId();
			if (queryList.size() == 1) {
				currentId = ((StockExchange) queryList.get(0)).getId();
			} else {
				nextId = "X10101";
			}
			;
			break;
		default:
			break;
		}

		if (!Util.isNullOrEmpty(currentId)) {
			StringBuilder prefix = new StringBuilder(currentId.substring(0, 1));
			int id = Integer.valueOf(currentId.substring(1)) + 1;
			nextId = prefix.append(String.valueOf(id)).toString();
		}

		return nextId;
	}

	public List readExcel(File file) {
		try {
			// 创建输入流，读取Excel
			InputStream is = new FileInputStream(file.getAbsolutePath());
			// jxl提供的Workbook类
			Workbook wb = Workbook.getWorkbook(is);
			// Excel的页签数量
			int sheet_size = wb.getNumberOfSheets();
			for (int index = 0; index < sheet_size; index++) {
				List<List> outerList = new ArrayList<List>();
				// 每个页签创建一个Sheet对象
				Sheet sheet = wb.getSheet(index);
				// sheet.getRows()返回该页的总行数
				for (int i = 0; i < sheet.getRows(); i++) {
					List innerList = new ArrayList();
					// sheet.getColumns()返回该页的总列数
					for (int j = 0; j < sheet.getColumns(); j++) {
						String cellinfo = sheet.getCell(j, i).getContents();
						if (cellinfo.isEmpty()) {
							continue;
						}
						innerList.add(cellinfo);
						System.out.print(cellinfo);
					}
					outerList.add(i, innerList);
					System.out.println();
				}
				return outerList;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
