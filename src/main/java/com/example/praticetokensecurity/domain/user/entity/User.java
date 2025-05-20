package com.example.praticetokensecurity.domain.user.entity;

import com.example.praticetokensecurity.common.entity.TimeStamped;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.like.entity.Like;
import com.example.praticetokensecurity.domain.user.dto.request.UserUpdateRequestDto;
import com.example.praticetokensecurity.domain.user.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
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

    @OneToMany(mappedBy = "user")
    private List<Book> books = new ArrayList<>();

    private boolean isDeleted = false;

    public User(String email, String password, UserRole userRole, String userName,
        String phoneNum) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.userName = userName;
        this.phoneNum = phoneNum;
    }

    public static User of(String email, String password, UserRole userRole, String userName,
        String phoneNum) {
        return new User(email, password, userRole, userName, phoneNum);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void update(UserUpdateRequestDto dto) {
        if (dto.getUserName() != null && !dto.getUserName().isBlank()) {
            this.userName = dto.getUserName();
        }
        if (dto.getPhoneNum() != null && !dto.getPhoneNum().isBlank()) {
            this.phoneNum = dto.getPhoneNum();
        }
    }

    public void updateLike(Like like) {
        this.likes.add(like);
    }

    public void delete() {
        this.isDeleted = true;
    }

}
