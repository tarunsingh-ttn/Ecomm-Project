package com.TTN.Ecommerce.entity;


import com.TTN.Ecommerce.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id=?")
@Where(clause = "is_deleted=false")
public class User extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize=1)
    private long user_id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;

    @JsonIgnore
    private String password;
    private boolean IS_DELETED;
    private boolean IS_ACTIVE;
    private boolean IS_EXPIRED;
    private boolean IS_LOCKED;
    private int INVALID_ATTEMPT_COUNT;
    private Date PASSWORD_UPDATE_DATE;

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", IS_DELETED=" + IS_DELETED +
                ", IS_ACTIVE=" + IS_ACTIVE +
                ", IS_EXPIRED=" + IS_EXPIRED +
                ", IS_LOCKED=" + IS_LOCKED +
                ", INVALID_ATTEMPT_COUNT=" + INVALID_ATTEMPT_COUNT +
                ", PASSWORD_UPDATE_DATE=" + PASSWORD_UPDATE_DATE +
                ", role=" + role +
                '}';
    }

    @OneToOne
    @JoinTable(name = "user_role",
            joinColumns ={@JoinColumn(name="user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "role_id")})


    private Role role;





}
