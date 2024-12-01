package com.example.databasedesign.repository;

import com.example.databasedesign.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = """
            select exists ( select 1 from users as u where u.id = :userId )
    """ , nativeQuery = true)
    long existsByUserId(@Param("userId") String userId);
}
