package com.bankmanagementsystem.operations.DTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankmanagementsystem.operations.Model.TransactionHistory;

@Repository
public interface transactionRepository extends JpaRepository<TransactionHistory,Long>{
 @Query(value="Select * from Transactions where bankid=:bankid",nativeQuery = true)
 List<TransactionHistory> finbByid(@Param("bankid") Long Bankid);
 
}
