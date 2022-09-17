package WKU.taxiStop.DTO;

import WKU.taxiStop.entity.DriverInfo;
import WKU.taxiStop.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

@Getter
@Setter
public class DriverDTO {

    private String driverName;
    private String carNumber;
    private String driverPhoneNumber;
    private String driverId;
    private String driverPw;

    public DriverInfo convert(DriverDTO driverDTO){

        DriverInfo driverInfo = new DriverInfo.Builder()
                .driverName(driverDTO.getDriverName())
                .driverId(driverDTO.getDriverId())
                .driverPw(driverDTO.getDriverPw())
                .driverPhoneNumber(driverDTO.getDriverPhoneNumber())
                .carNumber(driverDTO.getCarNumber())
                .build();

        return driverInfo;

    }

}
