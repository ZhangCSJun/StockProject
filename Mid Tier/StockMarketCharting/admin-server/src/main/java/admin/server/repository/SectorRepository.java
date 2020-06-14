package admin.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import admin.server.entity.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {

	@Query(value = "select * from sector order by id desc limit 1", nativeQuery = true)
	List<Sector> queryMaxId();
}
