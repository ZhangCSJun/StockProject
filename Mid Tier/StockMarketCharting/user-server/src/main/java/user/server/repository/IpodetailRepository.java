package user.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.server.entity.IpoDetail;

@Repository
public interface IpodetailRepository extends JpaRepository<IpoDetail, String> {
	@Query(value = "select * from ipodetails where companyname=:companyname", nativeQuery = true)
	List<IpoDetail> queryByCompanyName(String companyname);
}
