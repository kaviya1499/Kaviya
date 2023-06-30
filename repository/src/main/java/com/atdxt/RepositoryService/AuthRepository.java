package com.atdxt.RepositoryService;

import com.atdxt.Entity.Auth_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth_Entity, Integer> {
}
