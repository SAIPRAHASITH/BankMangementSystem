package com.bankmanagementsystem.operations.Model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table(name="Transactions")
@SequenceGenerator(name = "user_seq1", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TransactionHistory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "user_seq1")
private long id;

private long bankid;
private double amount;
private String tranasction_type;
private String Reciver_BankId;
public long getBankid() {
	return bankid;
}
public void setBankid(long bankid) {
	this.bankid = bankid;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getTranasction_type() {
	return tranasction_type;
}
public void setTranasction_type(String tranasction_type) {
	this.tranasction_type = tranasction_type;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getReciver_BankId() {
	return Reciver_BankId;
}
public void setReciver_BankId(String reciver_BankId) {
	this.Reciver_BankId = reciver_BankId;
}

}
