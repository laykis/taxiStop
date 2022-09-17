package WKU.taxiStop.controller;

import WKU.taxiStop.DTO.DispatchStatusDTO;
import WKU.taxiStop.DTO.DriverDTO;
import WKU.taxiStop.StaticStuff;
import WKU.taxiStop.entity.DriverInfo;
import WKU.taxiStop.entity.Log;
import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.repository.LogRepository;
import WKU.taxiStop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.stream.Collectors;



@RestController @RequiredArgsConstructor

public class AppController {

    private final DriverInfoRepository driverInfoRepository;
    private final AuthService authService;
    private final LogRepository logRepository;



    @PostMapping("/DriverLogin")
    public String login(String driverId, String driverPw){

        String loginId = driverInfoRepository.findDriverInfoByDriverId(driverId.trim()).getDriverId();
        String loginPw = driverInfoRepository.findDriverInfoByDriverId(driverId.trim()).getDriverPw();

        if( loginId != null){

            if(driverPw.trim().equals(loginPw)) return "login sucsess";

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

    @PostMapping("/EndService")
    public void endService(String token){


        DispatchStatusDTO dis = StaticStuff.dispatchStatusDTOList.stream()
                .filter(dispatchStatusDTO -> dispatchStatusDTO.getToken().equals(token))
                .findAny()
                .orElseThrow(NoSuchElementException::new);


        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        Log log = new Log.Builder()
                .token(token)
                .carNumber(dis.getCarNumber())
                .driverPhoneNumber(dis.getDriverPhoneNumber())
                .latitude(dis.getLatitude())
                .longitude(dis.getLongitude())
                .endDate(formatedNow)
                .build();

        logRepository.save(log);

    }


    @PostMapping("/NearRequest")
    public List<DispatchStatusDTO> getNearRequest(Double latitude, Double longitude, Double distance){
        return StaticStuff.dispatchStatusDTOList.stream()
                .filter(dto -> {
                    double x = (Math.cos(latitude) * 6400 * 2 * 3.14 / 360) * Math.abs(longitude - Double.parseDouble(dto.getLongitude()));
                    double y = 111 * Math.abs(latitude - Double.parseDouble(dto.getLatitude()));
                    return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2)) < distance;
                }).collect(Collectors.toList());
    }

}
