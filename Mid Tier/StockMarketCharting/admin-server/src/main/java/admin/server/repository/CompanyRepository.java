package admin.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import admin.server.entity.Company;
import admin.server.model.CompanyName;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>{

	@Query(value="select name, code from company where code like concat(:keyword,'%') or name like concat(:keyword,'%')", nativeQuery=true)
	List<CompanyName> queryCompanyByKeyWord(String keyword);

	@Query(value="select * from company where code like concat(:code,'%')", nativeQuery=true)
	List<Company> queryByCompanyCode(String code);
	
	@Modifying
	@Transactional
	@Query(value="update company set status=:status where id=:id", nativeQuery=true)
	int updateCompanyStatus(String id, String status);

	@Query(value="select * from company order by id desc limit 1", nativeQuery=true)
	List<Company> queryMaxId();
}
