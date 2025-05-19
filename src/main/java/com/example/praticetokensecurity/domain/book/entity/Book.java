package com.example.praticetokensecurity.domain.book.entity;

import com.example.praticetokensecurity.common.entity.TimeStamped;
import com.example.praticetokensecurity.domain.book.dto.AdminBookRequestDto;
import com.example.praticetokensecurity.domain.book.dto.AdminBookUpdateRequestDto;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import com.example.praticetokensecurity.domain.like.entity.Like;
import jakarta.persistence.CascadeType;
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
@Table(name = "book")
@NoArgsConstructor
@Getter
public class Book extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String authorName;

    private String publisher;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus = BookStatus.AVAILABLE;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Book(String title, String authorName, String publisher, BookStatus bookStatus) {
        this.title = title;
        this.authorName = authorName;
        this.publisher = publisher;
        this.bookStatus = bookStatus;
    }

    public static Book createInfo(AdminBookRequestDto dto) {
        return new Book(
            dto.getTitle(),
            dto.getAuthorName(),
            dto.getPublisher(),
            BookStatus.AVAILABLE
        );
    }

    public static void updateInfo(Book book, AdminBookUpdateRequestDto dto) {
        book.update(
            dto.getTitle(),
            dto.getAuthorName(),
            dto.getPublisher(),
            dto.getBookStatus()
        );
    }

    public void update(String title, String authorName, String publisher, BookStatus bookStatus) {
        this.title = title;
        this.authorName = authorName;
        this.publisher = publisher;
        this.bookStatus = bookStatus;
    }

}
