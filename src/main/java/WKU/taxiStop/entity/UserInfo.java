package WKU.taxiStop.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class UserInfo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;

    private String cardPw;

    private String uPhoneNumber;

    private String userName;

    public UserInfo(){

    }
    public static class Builder{
        private String cardPw;
        private String uPhoneNumber;
        private String userName;

        public Builder cardPw(String cardPw){
            this.cardPw = cardPw;
            return this;
        }

        public Builder uPhoneNumber(String uPhoneNumber){
            this.uPhoneNumber = uPhoneNumber;
            return this;
        }

        public Builder userName(String userName){
            this.userName = userName;
            return this;
        }

        public UserInfo build(){
            return new UserInfo(this);
        }
    }

    public UserInfo(Builder builder){
        this.cardPw = builder.cardPw;
        this.uPhoneNumber = builder.uPhoneNumber;
        this. userName = builder.userName;
    }
}
