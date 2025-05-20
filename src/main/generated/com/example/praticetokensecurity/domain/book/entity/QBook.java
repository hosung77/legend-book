package com.example.praticetokensecurity.domain.book.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = 1370338623L;

    public static final QBook book = new QBook("book");

    public final com.example.praticetokensecurity.common.entity.QTimeStamped _super = new com.example.praticetokensecurity.common.entity.QTimeStamped(this);

    public final StringPath authorName = createString("authorName");

    public final EnumPath<com.example.praticetokensecurity.domain.book.enums.BookStatus> bookStatus = createEnum("bookStatus", com.example.praticetokensecurity.domain.book.enums.BookStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.praticetokensecurity.domain.like.entity.Like, com.example.praticetokensecurity.domain.like.entity.QLike> likes = this.<com.example.praticetokensecurity.domain.like.entity.Like, com.example.praticetokensecurity.domain.like.entity.QLike>createList("likes", com.example.praticetokensecurity.domain.like.entity.Like.class, com.example.praticetokensecurity.domain.like.entity.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath publisher = createString("publisher");

    public final StringPath title = createString("title");

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

