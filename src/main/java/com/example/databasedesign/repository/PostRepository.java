package com.example.databasedesign.repository;

import com.example.databasedesign.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
            select p.*
            from post as p
            where p.id = :postId for update
    """ , nativeQuery = true)
    Optional<Post> findByPostIdForUpdate(Long postId);


    @Query(value = """
            SELECT p.id, u.nickname, p.title, p.content, p.created_at, p.like_count,
            IF(la.user_id IS NOT NULL, 1, 0) AS is_liked
            FROM post AS p
            INNER JOIN users AS u ON p.user_id = u.id
            LEFT JOIN like_action AS la ON la.user_id = u.id AND la.post_id = p.id
            WHERE p.id < :lastId
            ORDER BY p.id DESC
            limit 30
    """ , nativeQuery = true)
    List<PostQueryDto> findPosts(@Param("lastId") long lastId);


    @Query(value = """
            SELECT p.id, u.nickname, p.title, p.content, p.created_at, p.like_count,
            IF(la.user_id IS NOT NULL, true, false) AS is_liked
            from post p
            INNER join (select id from post ORDER BY like_count DESC, id desc limit :offset, 30) as tmp on p.id = tmp.id
            INNER JOIN users AS u ON p.user_id = u.id
            LEFT JOIN like_action AS la ON la.user_id = u.id AND la.post_id = p.id
    """ , nativeQuery = true)
    List<PostQueryDto> findPostsOrderByLikeCount(@Param("offset") long offset);

    @Query(value = """
            SELECT p.id, u.nickname, p.title, p.content, p.created_at, p.like_count, 1 AS is_liked
            from post p
            INNER JOIN users AS u ON p.user_id = u.id
            INNER JOIN like_action AS la ON la.post_id = p.id
            where la.user_id = :userId
            ORDER BY p.id DESC
            limit :offset, 30
    """ , nativeQuery = true)
    List<PostQueryDto> findUserLikedPosts(@Param("userId") String userId, @Param("offset") long offset);

}
