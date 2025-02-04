package com.green.greengram.feed;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByFeedIdAndWriterUser(Long feedId, User writerUser);

    //쿼리메소드를 delete, update 는 비추
    int deleteByFeedIdAndWriterUser(Long feedId, User writerUser);
// 쿼리메소드는 메소드 명으로 쿼리 만드느느거고 jpql은??
    //JPQL(Java Persistence Query Language)
    @Modifying // 이 애노테이션이 있어야 delete or update JPQL, 리턴타입은 void or int
    @Query("delete from Feed f where f.feedId=:feedId AND f.writerUser.userId=:writerUserId")

    int deleteFeed( Long feedId, Long writerUserId);
    /*
    Feed (대문자로 시작) - 클래스명 작성해야 함
     */
}


