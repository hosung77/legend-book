package com.example.praticetokensecurity.domain.like.entity;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "like_book")
@NoArgsConstructor
@Getter
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private boolean liked = false; // true = 좋아요, false = 취소

    public Like(User user, Book book, Boolean liked) {
        this.user = user;
        this.book = book;
        this.liked = liked;
    }

    public static Like of(User user, Book book, Boolean liked) {
        return new Like(user, book, liked);
    }

    public void toggle(){
        this.liked = !this.liked;
    }

}
