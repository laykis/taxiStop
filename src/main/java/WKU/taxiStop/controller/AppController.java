package WKU.taxiStop.controller;

import WKU.taxiStop.DTO.DispatchStatusDTO;
import WKU.taxiStop.DTO.DriverDTO;
import WKU.taxiStop.StaticStuff;
import WKU.taxiStop.entity.DriverInfo;
import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.repository.UserInfoRepository;
import WKU.taxiStop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final DriverInfoRepository driverInfoRepository;
    private final UserInfoRepository userInfoRepository;
    private final AuthService authService;


    @GetMapping("/DriverLogin")
    public String login(DriverDTO driverDTO){

        String loginId = driverInfoRepository.findDriverInfoByDriverId(driverDTO.getDriverId()).getDriverId();
        String loginPw = driverInfoRepository.findDriverInfoByDriverId(driverDTO.getDriverId()).getDriverPw();

        if( loginId != null){

            if(driverDTO.getDriverPw().trim().equals(loginPw)) return "login sucsess";

            return "fail";
        }

        return "fail";

    }

    @PostMapping("/DriverSignUp")
    public String signUp(DriverDTO driverDTO){

        try{

            if(!authService.validationCheck(driverDTO)) throw new Exception();

            DriverInfo driverInfo = driverDTO.convert(driverDTO);

            driverInfoRepository.save(driverInfo);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "sign up sucsess";


    }

    @GetMapping("/NearRequest")
    public List<DispatchStatusDTO> getNearRequest(double latitude, double longitude, double distance){
        return StaticStuff.dispatchStatusDTOList.stream()
                .filter(dto -> {
                    double x = (Math.cos(latitude) * 6400 * 2 * 3.14 / 360) * Math.abs(longitude - Double.parseDouble(dto.getLongitude()));
                    double y = 111 * Math.abs(latitude - Double.parseDouble(dto.getLatitude()));
                    return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2)) < distance;
                }).collect(Collectors.toList());
    }
}
