package com.TTN.Ecommerce.entity;


import com.TTN.Ecommerce.utils.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class VerificationToken extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;
    @Column(name="confirmation_token")
    private String verificationToken;
    @Transient
    private int expiryTimeInMinutes=5;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes)
    {   Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public VerificationToken(User user) {
        this.user = user;
        createdDate = new Date();
        verificationToken = UUID.randomUUID().toString();
        expiryDate= calculateExpiryDate(expiryTimeInMinutes);
    }

    public VerificationToken() {

    }
}