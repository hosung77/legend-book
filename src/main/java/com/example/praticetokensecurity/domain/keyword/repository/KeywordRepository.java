package com.example.praticetokensecurity.domain.keyword.repository;

import com.example.praticetokensecurity.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository <Keyword, Long>, KeywordRepositoryQuery {

}
