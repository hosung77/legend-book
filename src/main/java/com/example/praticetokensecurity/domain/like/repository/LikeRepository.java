package com.example.praticetokensecurity.domain.like.repository;

import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.like.entity.Like;
import com.example.praticetokensecurity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndBook(User user, Book book);

}
