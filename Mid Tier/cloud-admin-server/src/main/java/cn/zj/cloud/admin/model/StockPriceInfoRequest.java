package cn.zj.cloud.admin.model;

import java.util.List;

public class StockPriceInfoRequest {
	private List<String> companyCode;
	private String year;
	private String quater;
	public List<String> getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(List<String> companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getQuater() {
		return quater;
	}
	public void setQuater(String quater) {
		this.quater = quater;
	}
 
}
