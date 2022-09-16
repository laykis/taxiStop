package WKU.taxiStop.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class DriverInfo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long did;

    private String driverName;

    private String carNumber;

    private String dPhoneNumber;

    private String driverId;

    private String driverPw;

    public DriverInfo(){

    }

    public static class Builder{

        private String driverName;
        private String carNumber;
        private String dPhoneNumber;
        private String driverId;
        private String driverPw;

        public Builder driverName(String driverName){
            this.driverName = driverName;
            return this;
        }

        public Builder carNumber(String carNumber){
            this.carNumber = carNumber;
            return this;
        }

        public Builder dPhoneNumber(String dPhoneNumber){
            this.dPhoneNumber = dPhoneNumber;
            return this;
        }

        public Builder driverId(String driverId){
            this.driverId = driverId;
            return this;
        }

        public Builder driverPw(String driverPw){
            this.driverPw = driverPw;
            return this;
        }

        public DriverInfo build(){
            return new DriverInfo(this);
        }
    }

    public DriverInfo(Builder builder){
        this.driverName = builder.driverName;
        this.carNumber = builder.carNumber;
        this.dPhoneNumber = builder.dPhoneNumber;
        this.driverId = builder.driverId;
        this.driverPw = builder.driverPw;
    }


}
