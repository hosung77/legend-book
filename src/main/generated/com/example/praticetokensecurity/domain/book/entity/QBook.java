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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBook book = new QBook("book");

    public final com.example.praticetokensecurity.common.entity.QTimeStamped _super = new com.example.praticetokensecurity.common.entity.QTimeStamped(this);

    public final StringPath authorName = createString("authorName");

    public final EnumPath<com.example.praticetokensecurity.domain.book.enums.BookStatus> bookStatus = createEnum("bookStatus", com.example.praticetokensecurity.domain.book.enums.BookStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final ListPath<com.example.praticetokensecurity.domain.like.entity.Like, com.example.praticetokensecurity.domain.like.entity.QLike> likes = this.<com.example.praticetokensecurity.domain.like.entity.Like, com.example.praticetokensecurity.domain.like.entity.QLike>createList("likes", com.example.praticetokensecurity.domain.like.entity.Like.class, com.example.praticetokensecurity.domain.like.entity.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath publisher = createString("publisher");

    public final StringPath title = createString("title");

    public final com.example.praticetokensecurity.domain.user.entity.QUser user;

    public QBook(String variable) {
        this(Book.class, forVariable(variable), INITS);
    }

    public QBook(Path<? extends Book> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBook(PathMetadata metadata, PathInits inits) {
        this(Book.class, metadata, inits);
    }

    public QBook(Class<? extends Book> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.praticetokensecurity.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

