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

    private String userName;

    private String driverName;

    private String dPhoneNumber;

    private String carNumber;

    private String endDate;

    private String boardingLocation;



    public Log(){

    }
    public static class Builder{

        private String userName;
        private String driverName;
        private String dPhoneNumber;
        private String carNumber;
        private String endDate;
        private String boardingLocation;


        public Builder userName(String userName){
            this.userName = userName;
            return this;
        }

        public Builder driverName(String driverName){
            this.driverName = driverName;
            return this;
        }

        public Builder dPhoneNumber(String dPhoneNumber){
            this.dPhoneNumber = dPhoneNumber;
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

        public Builder boardingLocation(String boardingLocation){
            this.boardingLocation = boardingLocation;
            return this;
        }


        public Log build(){
            return new Log(this);
        }
    }

    public Log(Builder builder){
        this.userName = builder.userName;
        this.driverName = builder.driverName;
        this.dPhoneNumber = builder.dPhoneNumber;
        this.carNumber = builder.carNumber;
        this.endDate = builder.endDate;
        this.boardingLocation = builder.boardingLocation;

    }

}
