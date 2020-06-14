package admin.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import admin.server.entity.StockPrice;
import admin.server.model.StockPriceInfo;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, String> {
	@Query(value = "select * from stockprice order by id desc limit 1", nativeQuery = true)
	List<StockPrice> queryMaxId();

	@Query(value = "select * from stockprice where companycode=:companycode and date=:date and time=:time", nativeQuery = true)
	Optional<StockPrice> queryIsPresent(String companycode, String date, String time);

	@Query(value = "select C.name as companyname, S.maxprice, S.minprice, S.avgprice from ("
			+ "select base.companycode, max(base.maxprice) as maxprice, min(base.minprice) as minprice, cast(avg(base.avgprice) as decimal(10,2)) as avgprice from("
			+ "select companycode, max(currentprice) as maxprice, min(currentprice) as minprice, cast(avg(currentprice) as decimal(10,2)) as avgprice , date from stock_market.stockprice where companycode in(:companycode) "
			+ "group by companycode, date " + "having date like concat(:yearmonth, '/%'"
			+ ")) as base group by companycode) as S left outer join stock_market.company as C on S.companycode = C.code", nativeQuery = true)
	List<StockPriceInfo> queryStockPriceInfo(List<String> companycode, String yearmonth);
}
