package user.server.model;

import java.util.List;

import user.server.entity.Company;

public class CompanyResponse {

	private List<Company> CompanyList;

	public List<Company> getCompanyList() {
		return CompanyList;
	}

	public void setCompanyList(List<Company> companyList) {
		CompanyList = companyList;
	}

}
