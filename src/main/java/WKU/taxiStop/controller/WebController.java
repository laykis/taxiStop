package WKU.taxiStop.controller;

import WKU.taxiStop.DTO.DispatchStatusDTO;
import WKU.taxiStop.StaticStuff;
import WKU.taxiStop.entity.DriverInfo;
import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final AuthService authService;
    private final DriverInfoRepository driverInfoRepository;

    @GetMapping("/")
    public String main(@RequestParam String token, Model model){


        Optional<DispatchStatusDTO> dto = null;
        if((dto = StaticStuff.dispatchStatusDTOList.stream()
                .filter(dispatchStatusDTO -> dispatchStatusDTO.getToken().equals(token))
                .findAny()).isPresent()){
            if(!dto.get().getCallStatus().equalsIgnoreCase("0")) {
                model.addAttribute("driverName", dto.get().getDriverName());
                model.addAttribute("dPhoneNumber", dto.get().getDPhoneNumber());
                model.addAttribute("carNumber", dto.get().getCarNumber());
            }
            return "waiting";
        }


        model.addAttribute("token", token);

        return "index";

    }
    @PostMapping("/")
    public String tokenCheck(DispatchStatusDTO di){

        String result = "";

        try{

            String check = authService.checkUserAuth(di.getToken(), di.getPassword());

            if(check.trim().equals("sucsess")){

                DispatchStatusDTO user = new DispatchStatusDTO();

                user.setToken(di.getToken());
                user.setLatitude(di.getLatitude());
                user.setLongitude(di.getLongitude());
                user.setCallStatus("0");

                StaticStuff.dispatchStatusDTOList.add(user);

                result = "redirect:/?token="+ di.getToken();

            } else{

                result = "redirect:/";
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/accept")
    public String accept(String token, String did) {
        try {

            DriverInfo dr = driverInfoRepository.findById(Long.parseLong(did)).orElseThrow(NoSuchElementException::new);

            DispatchStatusDTO di = StaticStuff.dispatchStatusDTOList.stream().filter(dispatchStatusDTO -> dispatchStatusDTO.getToken().equals(token)).findAny().orElseThrow(NoSuchElementException::new);

            if (!di.getCallStatus().equals("0")) return "fail";

            di.setDriverName(dr.getDriverName());
            di.setDPhoneNumber(dr.getDPhoneNumber());
            di.setCarNumber(dr.getCarNumber());
            di.setCallStatus("1");

            return "OK";


        } catch (Exception e) {
            e.printStackTrace();

            return "fail";
        }

    }
}
