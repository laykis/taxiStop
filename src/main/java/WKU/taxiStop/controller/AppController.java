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


    /**
     * 로그인 시 호출
     * address : httpL//192.168.193.150:8080/DriverLogin
     * driverId : 택시 기사 아이디
     * driverPw: 택시 기사 비밀번호
     *
     * 로그인 성공 시 "login success" 반환
     * 로그인 실패 시 "fail" 반환
     */
    @PostMapping("/DriverLogin")
    public String login(String driverId, String driverPw){

        String loginId = driverInfoRepository.findDriverInfoByDriverId(driverId.trim()).getDriverId();
        String loginPw = driverInfoRepository.findDriverInfoByDriverId(driverId.trim()).getDriverPw();

        if( loginId != null){

            if(driverPw.trim().equals(loginPw)) return "login success";

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

        return "sucsess";

    }



    @PostMapping("/Accept")
    public String acceptCall(String token, String driverId){


        DriverInfo dri = driverInfoRepository.findDriverInfoByDriverId(driverId);

        DispatchStatusDTO dis = StaticStuff.dispatchStatusDTOList.stream()
                .filter(dispatchStatusDTO -> dispatchStatusDTO.getToken().equals(token))
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        System.out.println(dis.getCallStatus());
        if(dis.getCallStatus().trim().equals("0")){

            dis.setCallStatus("1");

            dis.setDriverName(dri.getDriverName());
            dis.setCarNumber(dri.getCarNumber());
            dis.setDriverPhoneNumber(dri.getDriverPhoneNumber());

            return "success";
        }

        return "fail";


    }



    @PostMapping("/BoardingComplete")
    public void boarding(String token, String driverId){
        System.out.println(token);
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
                .driverName(dis.getDriverName())
                .driverPhoneNumber(dis.getDriverPhoneNumber())
                .latitude(dis.getLatitude())
                .longitude(dis.getLongitude())
                .endDate(formatedNow)
                .build();

        logRepository.save(log);

        StaticStuff.dispatchStatusDTOList.remove(dis);

    }

    @PostMapping("/NearRequest")
    public List<DispatchStatusDTO> getNearRequest(Double latitude, Double longitude, Double distance){
        return StaticStuff.dispatchStatusDTOList.stream()
                .filter(dto -> {
                    double x = (Math.cos(latitude) * 6400 * 2 * 3.14 / 360) * Math.abs(longitude - Double.parseDouble(dto.getLongitude()));
                    double y = 111 * Math.abs(latitude - Double.parseDouble(dto.getLatitude()));
                    return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2)) < distance;
                }).filter(dto -> dto.getCallStatus().equals("0"))
                .collect(Collectors.toList());
    }


}
