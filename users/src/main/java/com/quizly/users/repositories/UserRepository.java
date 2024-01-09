package com.quizly.users.repositories;
import java.util.List;
import java.util.Optional;

import com.quizly.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("select u from User u where upper(u.username) like concat('%', upper(?1), '%')")
    List<User> queryUsername(String username);

}
