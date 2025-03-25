package com.bankmanagementsystem.operations.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bankmanagementsystem.operations.DTO.UserAccountRepository;
import com.bankmanagementsystem.operations.DTO.UserRepository;
import com.bankmanagementsystem.operations.Model.AccountHolder;
import com.bankmanagementsystem.operations.Model.UserLoginClass;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private UserAccountRepository accountant;
	public UserService(UserRepository userRepository,UserAccountRepository accountant) {
		this.accountant=accountant;
		this.userRepository = userRepository;
	}
	 @Transactional
	public void saveUser(UserLoginClass user) {
		UserLoginClass savedUser = userRepository.save(user);

        // Create account details with default balance = 0.0
        AccountHolder accountDetails = new AccountHolder();
        accountDetails.setId(savedUser.getId());
        accountDetails.setName(savedUser.getUsername());
        accountDetails.setAccount_Balance(0.0);
        accountDetails.setUserLoginClass(savedUser);

        // Save account details
        accountant.save(accountDetails);

       
	}

	public boolean authenticate(String id, String Password) {
		Optional<UserLoginClass> user = userRepository.findById(Long.parseLong(id));
		return user.map(value -> value.getPassword().equals(Password)).orElse(false);
	}
	
	public UserLoginClass getLastAddedUser() {
		return userRepository.findTopByOrderByIdDesc();
	}

	public UserLoginClass findByid(String id) {
		 Optional<UserLoginClass> user=userRepository.findById(Long.parseLong(id));
		 return user.get();
	}

}
