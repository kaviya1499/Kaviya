package com.atdxt.JdbcConnection.Repository;

import com.atdxt.JdbcConnection.Model.User;
import com.atdxt.JdbcConnection.Model.login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    @Query("SELECT id FROM User WHERE username = ?1")
    Integer findIdByUsername(String username);

}
