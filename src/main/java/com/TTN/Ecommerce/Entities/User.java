package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue
    private long user_id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean IS_DELETED;
    private boolean IS_ACTIVE;
    private boolean IS_EXPIRED;
    private boolean IS_LOCKED;
    private boolean INVALID_ATTEMPT_COUNT;
    private boolean PASSWORD_UPDATE_DATE;
}
