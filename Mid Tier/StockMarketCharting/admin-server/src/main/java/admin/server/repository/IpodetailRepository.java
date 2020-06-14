package admin.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import admin.server.entity.IpoDetail;

@Repository
public interface IpodetailRepository extends JpaRepository<IpoDetail, String> {
	@Query(value = "select * from ipodetails where companyname like concat(:companyname,'%')", nativeQuery = true)
	List<IpoDetail> queryByCompanyName(String companyname);

	@Query(value = "select * from ipodetails order by id desc limit 1", nativeQuery = true)
	List<IpoDetail> queryMaxId();

}
