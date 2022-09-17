package WKU.taxiStop.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispatchStatusDTO {

    private String token;
    private String password;
    private String latitude;
    private String longitude;
    private String driverName;
    private String carNumber;
    private String dPhoneNumber;
    private String callStatus;

}
