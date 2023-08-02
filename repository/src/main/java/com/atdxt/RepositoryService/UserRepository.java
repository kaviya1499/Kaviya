package com.atdxt.RepositoryService;

import com.atdxt.Entity.Auth_Entity;
import com.atdxt.Entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    @Query("SELECT u.id FROM UserEntity u WHERE u.authEntity = ?1")
    Integer findUserId(Auth_Entity authEntity);

    @Query("SELECT u.authEntity.id FROM UserEntity u WHERE u.detailsEntity.detid = ?1")
    Integer findByDetailsId(int detailsId);


}
