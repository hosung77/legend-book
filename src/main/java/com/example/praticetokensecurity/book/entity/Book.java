package com.example.praticetokensecurity.book.entity;

import com.example.praticetokensecurity.book.enums.BookStatus;
import com.example.praticetokensecurity.book.enums.Category;
import com.example.praticetokensecurity.common.entity.TimeStamped;
import com.example.praticetokensecurity.like.entity.Like;
import com.example.praticetokensecurity.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
public class Book extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;

    private String publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @Enumerated(EnumType.STRING)
    private Category category;

}
