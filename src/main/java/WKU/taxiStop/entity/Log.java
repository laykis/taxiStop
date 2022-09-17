package WKU.taxiStop.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lid;

    private String token;

    private String driverName;

    private String driverPhoneNumber;

    private String carNumber;

    private String endDate;

    private String latitude;

    private String longitude;



    public Log(){

    }
    public static class Builder{

        private String token;
        private String driverName;
        private String driverPhoneNumber;
        private String carNumber;
        private String endDate;
        private String latitude;
        private String longitude;



        public Builder token(String token){
            this.token = token;
            return this;
        }

        public Builder driverName(String driverName){
            this.driverName = driverName;
            return this;
        }

        public Builder driverPhoneNumber(String driverPhoneNumber){
            this.driverPhoneNumber = driverPhoneNumber;
            return this;
        }

        public Builder carNumber(String carNumber){
            this.carNumber = carNumber;
            return this;
        }

        public Builder endDate(String endDate){
            this.endDate = endDate;
            return this;
        }

        public Builder latitude(String latitude){
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(String longitude){
            this.longitude = longitude;
            return this;
        }


        public Log build(){
            return new Log(this);
        }
    }

    public Log(Builder builder){
        this.token = builder.token;
        this.driverName = builder.driverName;
        this.driverPhoneNumber = builder.driverPhoneNumber;
        this.carNumber = builder.carNumber;
        this.endDate = builder.endDate;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;


    }

}
