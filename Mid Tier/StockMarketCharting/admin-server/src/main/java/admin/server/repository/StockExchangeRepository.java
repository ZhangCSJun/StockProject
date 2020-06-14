package admin.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import admin.server.entity.StockExchange;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, String> {
	@Modifying
	@Transactional
	@Query(value = "delete from stockexchange where id=:id", nativeQuery = true)
	void deleteById(String id);

	@Query(value = "select * from stockexchange order by id desc limit 1", nativeQuery = true)
	List<StockExchange> queryMaxId();
}
