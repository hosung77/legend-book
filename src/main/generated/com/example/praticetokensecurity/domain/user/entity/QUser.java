package com.example.praticetokensecurity.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 702731267L;

    public static final QUser user = new QUser("user");

    public final com.example.praticetokensecurity.common.entity.QTimeStamped _super = new com.example.praticetokensecurity.common.entity.QTimeStamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.praticetokensecurity.domain.like.entity.Like, com.example.praticetokensecurity.domain.like.entity.QLike> likes = this.<com.example.praticetokensecurity.domain.like.entity.Like, com.example.praticetokensecurity.domain.like.entity.QLike>createList("likes", com.example.praticetokensecurity.domain.like.entity.Like.class, com.example.praticetokensecurity.domain.like.entity.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    public final StringPath userName = createString("userName");

    public final EnumPath<com.example.praticetokensecurity.domain.user.enums.UserRole> userRole = createEnum("userRole", com.example.praticetokensecurity.domain.user.enums.UserRole.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

