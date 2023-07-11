package com.atdxt.RepositoryService;

import com.atdxt.Entity.Details_Entity;
import com.atdxt.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<Details_Entity, Integer> {
    Optional<Details_Entity> findByEmail(String email);
}
