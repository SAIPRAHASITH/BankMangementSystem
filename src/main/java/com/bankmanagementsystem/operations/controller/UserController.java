package com.bankmanagementsystem.operations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankmanagementsystem.operations.Model.AccountHolder;
import com.bankmanagementsystem.operations.Model.UserLoginClass;
import com.bankmanagementsystem.operations.Service.AccounterService;
import com.bankmanagementsystem.operations.Service.UserService;

@Controller
public class UserController {
	private final UserService userService;
	private final AccounterService accountadding;
	private static AccountHolder acc;

	public UserController(UserService userService,AccounterService accountadding) {
		this.userService = userService;
		this.accountadding = accountadding;
	}
	 @ModelAttribute("acc")
	    public AccountHolder getSessionAccount() {
	        return new AccountHolder();  // Prevent null on refresh
	    }

	@GetMapping("/login")
	public String showloginPage() {
		return "login";
	}
	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}
	@GetMapping("/AccountHolder")
	public String accountHolder() {
		return "AccountHolder";
	}
	@GetMapping("/deposite")
	public String deposite() {
		return "deposite";
	}
	@GetMapping("/Withdraw")
	public String withDraw() {
		return "withDraw";
	}

	@PostMapping("/logging")
	public String login(@RequestParam String id, @RequestParam String password, Model model) {
		UserLoginClass loggeduser=userService.findByid(id);
		acc=accountadding.findById(Long.parseLong(id));
		if (userService.authenticate(id, password)) {
		
			model.addAttribute("user",loggeduser);
			
			return "AccountHolder";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "login";

		}

	}

	@PostMapping("/registering")
	public String register(@RequestParam String name, @RequestParam String password, @RequestParam String username,Model model) {
		UserLoginClass newUser = new UserLoginClass();
		newUser.setName(name);
		newUser.setUsername(username);
		newUser.setPassword(password);
		userService.saveUser(newUser);
		
		model.addAttribute("message", "Succesfully Added New User");
		return "redirect:/UserDetails";
	}
	@GetMapping("/UserDetails")
	public String showlastUserDetails(Model model) {
		UserLoginClass user=userService.getLastAddedUser();
		model.addAttribute("user",user);
		return "success";
	}
	@GetMapping("/checkbalance")
	public String checkBalance(Model model) {
	    AccountHolder account = acc;
	    double balance=accountadding.balance(acc.getId());

	    if (account != null) {
	        model.addAttribute("balance",balance);
	        return "balance"; // Show balance
	    } else {
	        model.addAttribute("error", "Account not found");
	        return "accountHolder"; // Stay on the same page
	    }
	}
	@PostMapping("/depositing")
	public String depositing(@RequestParam String value,Model model) {
		AccountHolder depositer=acc;
		if(depositer==null) {
			return "login";
		}
		//System.out.println(accountadding.deposite(Double.parseDouble(value),depositer.getId()));
		if(accountadding.deposite(Double.parseDouble(value) , depositer.getId())) {
		 return "redirect:/checkbalance";
	}
		else {
			model.addAttribute("deposite","Amount not added to your Account");
			return "deposite";
		}
		}
	
	@PostMapping("/withdrawing")
	public String withDrawing(@RequestParam String value,Model model) {
		AccountHolder withdrawer=acc;
		if(withdrawer==null) {
			return "login";
		}
		boolean status=accountadding.WithDraw(Double.parseDouble(value), withdrawer.getId());
				if(status) {
					 return "redirect:/checkbalance";
				}
				else {
					model.addAttribute("withdraw", "InSuffcient Account Balance");
					return "withDraw";
				}
				
				
		
	}

	
}