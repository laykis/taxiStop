package WKU.taxiStop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptDTO {

    private String token;
    private String driverName;
    private String carNumber;
    private String dPhoneNumber;
}
