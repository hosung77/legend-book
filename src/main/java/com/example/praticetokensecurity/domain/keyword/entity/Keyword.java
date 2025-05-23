package com.example.praticetokensecurity.domain.keyword.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keywords")
@Getter
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public Keyword() {
    }

    public static Keyword of(String keyword) {
        return new Keyword(keyword);
    }

}
