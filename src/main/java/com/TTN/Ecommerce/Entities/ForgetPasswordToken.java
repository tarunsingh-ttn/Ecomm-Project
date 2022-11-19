/*
package com.TTN.Ecommerce.Entities;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class ForgetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String forgetPasswordtoken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;




    public ForgetPasswordToken(User user) {
        this.user = user;
        createdDate = new Date();
        forgetPasswordtoken = UUID.randomUUID().toString();
    }

    public  ForgetPasswordToken() {

    }
}*/
