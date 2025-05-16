package com.example.praticetokensecurity.domain.user.entity;

import com.example.praticetokensecurity.common.entity.TimeStamped;
import com.example.praticetokensecurity.domain.like.entity.Like;
import com.example.praticetokensecurity.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="users")
@NoArgsConstructor
@Getter
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String userName;

    private String phoneNum;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public User(String email, String password, UserRole userRole, String userName, String phoneNum){
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.userName = userName;
        this.phoneNum = phoneNum;
    }

    public static User of (String email, String password, UserRole userRole, String userName, String phoneNum){
        return new User(email,password,userRole, userName, phoneNum);
    }
}
