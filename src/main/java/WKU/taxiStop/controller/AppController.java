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

import java.util.NoSuchElementException;

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

    @PostMapping("/Accept")
    public String acceptCall(String token, Long did){


        DispatchStatusDTO dis = StaticStuff.dispatchStatusDTOList.stream()
                .filter(dispatchStatusDTO -> dispatchStatusDTO.getToken().equals(token))
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        if(dis.getCallStatus().trim().equals("0")){

            dis.setCallStatus("1");

            return "배차 완료됬습니다.";
        }

        return "이미 배차된 요청입니다.";


    }


    @PostMapping("/BoardingComplete")
    public void boarding(String token, Long did){

        DispatchStatusDTO dis = StaticStuff.dispatchStatusDTOList.stream()
                .filter(dispatchStatusDTO -> dispatchStatusDTO.getToken().equals(token))
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        dis.setCallStatus("2");

    }

}
