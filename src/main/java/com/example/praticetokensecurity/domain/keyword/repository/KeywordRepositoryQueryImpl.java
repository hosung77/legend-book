package com.example.praticetokensecurity.domain.keyword.repository;

import static com.querydsl.core.types.dsl.Wildcard.count;

import com.example.praticetokensecurity.domain.keyword.dto.response.Top5KeywordResponseDto;
import com.example.praticetokensecurity.domain.keyword.entity.QKeyword;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeywordRepositoryQueryImpl implements KeywordRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Top5KeywordResponseDto> findTop5Keywords() {
        QKeyword keyword = QKeyword.keyword1;

        List<Top5KeywordResponseDto> result = jpaQueryFactory
            .select(Projections.constructor(
                Top5KeywordResponseDto.class,
                keyword.keyword,
                count
                ))
            .from(keyword)
            .groupBy(keyword.keyword)
            .orderBy(count.desc())
            .limit(5)
            .fetch();

        return result;
    }
}
