package WKU.taxiStop.repository;

import WKU.taxiStop.entity.DriverInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface DriverInfoRepository extends JpaRepository<DriverInfo, Long> {

    DriverInfo findDriverInfoByDriverId(String driverId);

    DriverInfo findDriverInfoByCarNumber(String carNumber);

    DriverInfo findDriverInfoByDriverPhoneNumber(String driverPhoneNumber);
}
