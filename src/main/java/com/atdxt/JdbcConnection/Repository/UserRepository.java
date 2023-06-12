package com.atdxt.JdbcConnection.Repository;

import com.atdxt.JdbcConnection.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
}
