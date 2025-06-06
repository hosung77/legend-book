package com.example.praticetokensecurity.domain.book.repository;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    Page<Book> findByTitle(String keyword, Pageable pageable);

    Page<Book> findByUserIdAndBookStatus(Long userId, BookStatus bookStatus, Pageable pageable);

    Optional<Book> findByIdAndIsDeletedFalse(Long id);

    Page<Book> findAllByIsDeletedFalse(Pageable pageable);

    List<Book> findAllByUserIdAndBookStatus(Long userId, BookStatus bookStatus);


}
