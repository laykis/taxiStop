package WKU.taxiStop.service;

import WKU.taxiStop.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepositroy;

    public String checkUserAuth(String token, String userPw){

        String result = "";

        try{

            Long uid = Long.parseLong(token);

            String pw = userInfoRepositroy.findById(uid).orElseThrow(NoSuchElementException::new).toString();

            if(userPw.trim().equals(pw)){

                result = "sucsess";

            }else {
                result = "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return result;
    }

}
