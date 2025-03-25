package com.bankmanagementsystem.operations.DTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bankmanagementsystem.operations.Model.UserLoginClass;
@Repository
public interface UserRepository extends JpaRepository<UserLoginClass, Long> {
  Optional<UserLoginClass> findById(long id);
  
  @Query(value = "SELECT * FROM user_credentials  ORDER BY id DESC LIMIT 1", nativeQuery = true)
  UserLoginClass findTopByOrderByIdDesc();
}
