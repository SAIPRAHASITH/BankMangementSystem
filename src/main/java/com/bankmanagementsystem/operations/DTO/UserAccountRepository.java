package com.bankmanagementsystem.operations.DTO;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankmanagementsystem.operations.Model.AccountHolder;
@Repository
public interface UserAccountRepository extends JpaRepository<AccountHolder, Long> {
	Optional<AccountHolder> findById(long id);
}
