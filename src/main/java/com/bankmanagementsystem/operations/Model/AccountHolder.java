package com.bankmanagementsystem.operations.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Account_Holder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder {
  @Id
  private long id;
  @Column(unique = true,nullable = false)
  private String name;
  @Column(nullable = false)
  private double Account_Balance;
  @OneToOne
  @MapsId  // This ensures 'id' is the same as UserLoginClass.id
  @JoinColumn(name = "id")
  private UserLoginClass userLoginClass;
  public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getAccount_Balance() {
	return Account_Balance;
}
public void setAccount_Balance(double account_Balance) {
	Account_Balance = account_Balance;
}
public UserLoginClass getUserLoginClass() {
	return userLoginClass;
}
public void setUserLoginClass(UserLoginClass userLoginClass) {
	this.userLoginClass = userLoginClass;
}
@Version  // Added versioning for optimistic locking
private Integer version;

}
