package com.bankmanagementsystem.operations.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bankmanagementsystem.operations.DTO.UserAccountRepository;
import com.bankmanagementsystem.operations.DTO.UserRepository;
import com.bankmanagementsystem.operations.Model.AccountHolder;
@Service
public class AccounterService {
	
	private UserAccountRepository accountant;
	
	public AccounterService(UserAccountRepository accountant, UserRepository userRepository) {
	
		this.accountant = accountant;
		
	}
	
	public AccountHolder findById(long id) {
		Optional<AccountHolder> acc=accountant.findById(id);
		return acc.get();
	}
	public boolean deposite(double amount,Long id) {
		Optional<AccountHolder> acc=accountant.findById(id);
		if(acc!=null) {
		AccountHolder depositer=acc.get();
		double balance=depositer.getAccount_Balance()+amount;
		depositer.setAccount_Balance(balance);
		System.out.println(depositer.getAccount_Balance());
		accountant.save(depositer);
		return true;
		}
		return false;
	}

	public double balance(long id) {
		Optional<AccountHolder> acc=accountant.findById(id);
		return acc.get().getAccount_Balance();
	}
	public boolean WithDraw(double amount,Long id) {
		boolean status=false;
		Optional<AccountHolder> acc=accountant.findById(id);
		if(acc!=null) {
		AccountHolder depositer=acc.get();
		if(amount<=depositer.getAccount_Balance()) {
			double balance=depositer.getAccount_Balance()-amount;
			depositer.setAccount_Balance(balance);
		    System.out.println(depositer.getAccount_Balance());
		    accountant.save(depositer);
		   status=true;
		}
		else {
			status=false;
		}
	}
		return status;

}}
