package admin.server.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="ipodetails")
@IdClass(IpoDetailPrimaryKey.class)
public class IpoDetail{

	@Id
	@Column(name="id")
	private String id;
	@Id
	@Column(name="companyname")
	private String companyName;
	@Id
	@Column(name="stockexchange")
	private String stockExchange;	
	@Column(name="pricepershare")
	private BigDecimal pricePerShare;
	@Column(name="totalnumberofshares")
	private int totalNumberOfShares;
	@Column(name="opendatetime")
	private String openDatetime;
	@Column(name="remark")
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStockExchange() {
		return stockExchange;
	}
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	public BigDecimal getPricePerShare() {
		return pricePerShare;
	}
	public void setPricePerShare(BigDecimal pricePerShare) {
		this.pricePerShare = pricePerShare;
	}
	public int getTotalNumberOfShares() {
		return totalNumberOfShares;
	}
	public void setTotalNumberOfShares(int totalNumberOfShares) {
		this.totalNumberOfShares = totalNumberOfShares;
	}
	public String getOpenDatetime() {
		return openDatetime;
	}
	public void setOpenDatetime(String openDatetime) {
		this.openDatetime = openDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
