package WKU.taxiStop.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class log {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lid;

    private String userName;

    private String driverName;

    private String dPhoneNumber;

    private String carNumber;

    private String endDate;

    private String xDeparture;

    private String yDeparture;

    private String xArrival;

    private String yArrival;

    public static class Builder{

        private String userName;
        private String driverName;
        private String dPhoneNumber;
        private String carNumber;
        private String endDate;
        private String xDeparture;
        private String yDeparture;
        private String xArrival;
        private String yArrival;

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

        public Builder xDeparture(String xDeparture){
            this.xDeparture = xDeparture;
            return this;
        }

        public Builder yDeparture(String yDeparture){
            this.yDeparture = yDeparture;
            return this;
        }

        public Builder xArrival(String xArrival){
            this.xArrival = xArrival;
            return this;
        }

        public Builder yArrival(String yArrival){
            this.yArrival = yArrival;
            return this;
        }

        public log build(){
            return new log(this);
        }
    }

    public log(Builder builder){
        this.userName = builder.userName;
        this.driverName = builder.driverName;
        this.dPhoneNumber = builder.dPhoneNumber;
        this.carNumber = builder.carNumber;
        this.endDate = builder.endDate;
        this.xDeparture = builder.xDeparture;
        this.yDeparture = builder.yDeparture;
        this.xArrival = builder.xArrival;
        this.yArrival = builder.yArrival;
    }

}
