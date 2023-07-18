package com.atdxt.RepositoryService;

import com.atdxt.Entity.Auth_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth_Entity, Integer> {
    Optional<Auth_Entity> findByUsername(String username);
    @Query("SELECT id FROM Auth_Entity WHERE username = ?1")
    Integer findIdByUsername(String username);
}
