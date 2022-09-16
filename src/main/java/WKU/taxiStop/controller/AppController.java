package WKU.taxiStop.controller;

import WKU.taxiStop.repository.DriverInfoRepository;
import WKU.taxiStop.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final DriverInfoRepository driverInfoRepository;
    private final UserInfoRepository userInfoRepository;


}
