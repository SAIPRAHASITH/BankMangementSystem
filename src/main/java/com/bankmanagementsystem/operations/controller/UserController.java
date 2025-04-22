package com.bankmanagementsystem.operations.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AccounterService accountadding;

	@Autowired
	private HttpSession session;
	
	
	@ModelAttribute("acc")
	public AccountHolder getSessionAccount() {
		return new AccountHolder(); // Prevent null on refresh
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
	@GetMapping("/Transfer")
	public String Transfer() {
		return "Transfer";
	}
	@GetMapping("/transfer3")
	public String showTransferPage() {
	    return "Transfer3";
	}
	@GetMapping("/Transactions")
	public String showTransactionHistory() {
	    return "redirect:/History";
	}
	@PostMapping("/logging")
	public String login(@RequestParam String id, @RequestParam String password, Model model) {
		
		if (userService.authenticate(id, password)) {
			UserLoginClass loggeduser = userService.findByid(id);
			session.setAttribute("loggeduser", loggeduser);
			session.setAttribute("accountholder",accountadding.findById(Long.parseLong(id)) );
			model.addAttribute("user", loggeduser);

			return "AccountHolder";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "login";

		}

	}

	@PostMapping("/registering")
	public String register(@RequestParam String name, @RequestParam String password, @RequestParam String username,
			Model model) {
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
		UserLoginClass user = userService.getLastAddedUser();
		model.addAttribute("user", user);
		return "success";
	}

	@GetMapping("/checkbalance")
	public String checkBalance(Model model) {
		AccountHolder a=(AccountHolder)session.getAttribute("accountholder");
		AccountHolder account = accountadding.findById(a.getId());
		double balance = accountadding.balance(account.getId());
		if (account != null) {
			model.addAttribute("balance", balance);
			return "balance"; // Show balance
		} else {
			model.addAttribute("error", "Account not found");
			return "accountHolder"; // Stay on the same page
		}
	}

	@PostMapping("/depositing")
	public String depositing(@RequestParam String value, Model model) {
		AccountHolder a=(AccountHolder)session.getAttribute("accountholder");
		AccountHolder depositer = accountadding.findById(a.getId());
		if (depositer == null) {
			return "login";
		}
		// System.out.println(accountadding.deposite(Double.parseDouble(value),depositer.getId()));
		if (accountadding.deposite(Double.parseDouble(value), depositer.getId())) {
			return "redirect:/checkbalance";
		} else {
			model.addAttribute("deposite", "Amount not added to your Account");
			return "deposite";
		}
	}

	@PostMapping("/withdrawing")
	public String withDrawing(@RequestParam String value, Model model) {
		AccountHolder a=(AccountHolder)session.getAttribute("accountholder");
		AccountHolder withdrawer  = accountadding.findById(a.getId());
		if (withdrawer == null) {
			return "login";
		}
		boolean status = accountadding.WithDraw(Double.parseDouble(value), withdrawer.getId());
		if (status) {
			return "redirect:/checkbalance";
		} else {
			model.addAttribute("withdraw", "InSuffcient Account Balance");
			return "withDraw";
		}

	}
    @PostMapping("/Transfering")
    public String Transferring(@RequestParam String Account_No,Model model) {
    	if(Account_No.equals("")) {
    		model.addAttribute("transfer", "Please Enter Any Bank Account Number");
    		return "Transfer";
    	}
    	AccountHolder a=(AccountHolder)session.getAttribute("accountholder");
		AccountHolder sender  = accountadding.findById(a.getId());
		
		AccountHolder receiver = accountadding.findById(Long.parseLong(Account_No));
		session.setAttribute("receiver", receiver);
        if(receiver==null) {
        	model.addAttribute("transfer", "Invalid Bank Account No");
        	return "Transfer";
        }
        else {
        	if(sender.getId()==receiver.getId()) {
        		model.addAttribute("transfer", "Doesn't Enter Your Bank Account");
        		return "Transfer";
        	}
        	else {
        		UserLoginClass loggedreciver=userService.findByid(Account_No);
        		model.addAttribute("loggedreciver",loggedreciver);
        		model.addAttribute("reciverid", loggedreciver);
        		return "Transfer2";
        	}
        }
    	
    }
    @PostMapping("/Amountsent")
    public String amountSending(@RequestParam String Amount, Model model) {
    	AccountHolder a=(AccountHolder)session.getAttribute("accountholder");
		AccountHolder sender  = accountadding.findById(a.getId());
		AccountHolder b=(AccountHolder)session.getAttribute("receiver");
		AccountHolder receiver=accountadding.findById(b.getId());
		String status=accountadding.transfermoney(sender.getId(),receiver.getId(),Amount); 
		if(status.equals("Successfully Send Amount")) {
			model.addAttribute("successMessage",status);
			return "transfer3";
		}
		else {
			model.addAttribute("errorMessage",status);
			return "transfer3";
		}
    
}
    @GetMapping("/History")
    public String viewTestCasesdata(Model model) {
    	AccountHolder a=(AccountHolder)session.getAttribute("accountholder");
    	  model.addAttribute("TransactionHistory",accountadding.getAllRecords(a.getId()));
    	  return "TransactionHistory";
    }
   
  @GetMapping("/goback")
  public String home() {
	  return "login";
  }

}