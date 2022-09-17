package WKU.taxiStop.service;

import WKU.taxiStop.DTO.DriverDTO;
import WKU.taxiStop.entity.UserInfo;
import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepositroy;
    private final DriverInfoRepository driverInfoRepository;


    public boolean validationCheck(DriverDTO driverDTO){

        if(driverInfoRepository.findDriverInfoByDriverId(driverDTO.getDriverId()) != null) return false;

        if(driverInfoRepository.findDriverInfoByCarNumber(driverDTO.getCarNumber()) != null) return false;

        if(driverInfoRepository.findDriverInfoByDPhoneNumber(driverDTO.getDPhoneNumber()) != null) return false;

        return true;
    }

    public String checkUserAuth(String token, String password){

        String result = "";

        try{



            String pw = userInfoRepositroy.findById(token).orElseThrow(NoSuchElementException::new).getCardPw();

            if(password.trim().equals(pw)){

                result = "sucsess";

            }else {
                result = "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return result;
    }

//    public void test(String token){
//
//
//
//        UserInfo userInfo = new UserInfo.Builder()
//                .cardPw("1234")
//                .build();
//
//        userInfo.settingToken(token);
//
//        userInfoRepositroy.save(userInfo);
//
//
//    }

}
