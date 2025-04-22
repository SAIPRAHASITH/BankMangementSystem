package com.bankmanagementsystem.operations.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankmanagementsystem.operations.DTO.UserAccountRepository;
import com.bankmanagementsystem.operations.DTO.UserRepository;
import com.bankmanagementsystem.operations.DTO.transactionRepository;
import com.bankmanagementsystem.operations.Model.AccountHolder;
import com.bankmanagementsystem.operations.Model.TransactionHistory;
@Service
public class AccounterService {
	
	private UserAccountRepository accountant;
	@Autowired
	private transactionRepository tranascations;
	
	

	public AccounterService(UserAccountRepository accountant, UserRepository userRepository,TransactionHistory t1) {
	
		this.accountant = accountant;
		
	}
	
	public AccountHolder findById(long id) {
		
		return accountant.findById(id)
	            .orElse(null);
	}
	public boolean deposite(double amount,Long id) {
		
		Optional<AccountHolder> acc=accountant.findById(id);
		if(acc!=null) {
		AccountHolder depositer=acc.get();
		double balance=depositer.getAccount_Balance()+amount;
		depositer.setAccount_Balance(balance);
		System.out.println(depositer.getAccount_Balance());
		TransactionHistory t1=new TransactionHistory();
		t1.setBankid(id);
		t1.setTranasction_type("deposite");
		t1.setAmount(amount);
		t1.setReciver_BankId("Self");
		tranascations.save(t1);
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
		AccountHolder withdrawer=acc.get();
		if(amount<=withdrawer.getAccount_Balance()) {
			double balance=withdrawer.getAccount_Balance()-amount;
			withdrawer.setAccount_Balance(balance);
			TransactionHistory t1=new TransactionHistory();
			t1.setBankid(id);
			t1.setTranasction_type("WithDraw");
			t1.setAmount(amount);
			t1.setReciver_BankId("Self");
			tranascations.save(t1);
		    accountant.save(withdrawer);
		   status=true;
		}
		else {
			status=false;
		}
	}
		return status;

}

	public String transfermoney(long id1, long id2, String amount) {
		String message="";
		Optional<AccountHolder> acc1=accountant.findById(id1);
		Optional<AccountHolder> acc2=accountant.findById(id2);
		double Amount=Double.parseDouble(amount);
		if(acc1!=null) {
			AccountHolder sender=acc1.get();
			AccountHolder receiver=acc2.get();
			if(Amount<=sender.getAccount_Balance()) {
				double senderbalance=sender.getAccount_Balance()-Amount;
				sender.setAccount_Balance(senderbalance);
				TransactionHistory t1=new TransactionHistory();
				t1.setBankid(id1);
				t1.setTranasction_type("Send");
				t1.setAmount(Double.parseDouble(amount));
				t1.setReciver_BankId(String.valueOf(id2));
				tranascations.save(t1);
				double receiverbalance=receiver.getAccount_Balance()+Amount;
				receiver.setAccount_Balance(receiverbalance);
				TransactionHistory t2=new TransactionHistory();
				t2.setBankid(id2);
				t2.setTranasction_type("Receive");
				t2.setAmount(Double.parseDouble(amount));
				t2.setReciver_BankId(String.valueOf(id1));
				tranascations.save(t2);
				accountant.save(sender);
				accountant.save(receiver);
				message="Successfully Send Amount";
				
			}
			else {
				message="Insuffcient Account Balance";
			}
		}
		else {
			message="Invalid user";
		}
			
		
		return message;		
	}
	public List<TransactionHistory> getAllRecords(long id){
	   return tranascations.finbByid(id);
	}
	}
