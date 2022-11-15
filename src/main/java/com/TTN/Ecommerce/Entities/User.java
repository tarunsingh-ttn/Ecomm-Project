package com.TTN.Ecommerce.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize=1)
    private long user_id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private boolean IS_DELETED;
    private boolean IS_ACTIVE;
    private boolean IS_EXPIRED;
    private boolean IS_LOCKED;
    private boolean INVALID_ATTEMPT_COUNT;
    private boolean PASSWORD_UPDATE_DATE;

    @OneToOne
    @JoinTable(name = "user_role",
            joinColumns ={@JoinColumn(name="user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "role_id")})
    private Role role;





}
