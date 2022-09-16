package WKU.taxiStop.controller;

import WKU.taxiStop.DTO.TokenCheckDTO;
import WKU.taxiStop.DTO.UserInfoDTO;
import WKU.taxiStop.StaticStuff;
import WKU.taxiStop.entity.DriverInfo;
import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final AuthService authService;
    private final DriverInfoRepository driverInfoRepository;

    @PostMapping("/")
    public String tokenCheck(@RequestParam("token") String token, TokenCheckDTO to){

        String result = "";

        try{

            String check = authService.checkUserAuth(token, to.getUserPw());

            if(check.trim().equals("sucsess")){

                UserInfoDTO user = new UserInfoDTO();

                user.setUserToken(token);
                user.setXPosition(to.getXPosition());
                user.setYPosition(to.getYPosition());

                StaticStuff.callList.add(user);

                result = "wating";

            } else{

                result = "redirect:/";
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/")
    public void accept(String token, String did){

        try{

            DriverInfo dr = driverInfoRepository.findById(Long.parseLong(did)).orElseThrow(NoSuchElementException::new);


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
