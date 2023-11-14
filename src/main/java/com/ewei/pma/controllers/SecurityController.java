package com.ewei.pma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ewei.pma.dao.IEmployeeRepository;
import com.ewei.pma.dao.UserAccountRepository;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.UserAccount;

@Controller
public class SecurityController {
	
	@Autowired
	UserAccountRepository accountRepo;

	@Autowired
	IEmployeeRepository empRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;

	@GetMapping("/login") 
	public String login() {
		return "security/login";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		// UserAccount userAccount = new UserAccount();
		// model.addAttribute("userAccount", userAccount);
		
		return "security/register-edit";
	}
	
	@PostMapping("/register/save")
	public String saveUser(Model model, Employee employee) {
		employee.setPassword(bCryptEncoder.encode(employee.getPassword()));
		empRepo.save(employee);
		return "redirect:/";
	}
	
}
