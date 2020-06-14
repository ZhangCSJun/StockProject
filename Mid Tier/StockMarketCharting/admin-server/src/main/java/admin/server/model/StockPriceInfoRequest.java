package admin.server.model;

import java.util.List;

public class StockPriceInfoRequest {
	private String queryType;
	private List<String> companyCode;
	private String year;
	private String quater;

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

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
