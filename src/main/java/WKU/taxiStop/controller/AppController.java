package WKU.taxiStop.controller;

import WKU.taxiStop.DTO.DriverDTO;
import WKU.taxiStop.entity.DriverInfo;
import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.repository.UserInfoRepository;
import WKU.taxiStop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
