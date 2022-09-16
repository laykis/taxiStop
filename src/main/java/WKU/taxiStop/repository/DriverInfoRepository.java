package WKU.taxiStop.repository;

import WKU.taxiStop.entity.DriverInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverInfoRepository extends JpaRepository<DriverInfo, Long> {
}
