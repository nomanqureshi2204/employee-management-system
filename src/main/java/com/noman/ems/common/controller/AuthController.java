package com.noman.ems.common.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;
import com.noman.ems.client.service.ClientService;
import com.noman.ems.common.entity.Token;
import com.noman.ems.common.repository.TokenRepository;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;
import com.noman.ems.employee.service.EmployeeService;

@RestController
public class AuthController {
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired 
	private ClientRepository clientRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping("/set-password")
	public String setPassword(@RequestParam String token,@RequestParam String password) {
		
		// 1. Token check
		Token tk = tokenRepo.findById(token)
				   .orElseThrow(()->new RuntimeException("Invalid token"));
		
		// 2. Expiry check 
		if(tk.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Token expired");
		}
		
		// 3. already used check 
		if(tk.isUsed()) {
			throw new RuntimeException("Token already used");
		}
		
		String email = tk.getEmail();
		
		//4. same logic for both (employee+client)
		Employee emp = empRepo.findByEmail(email).orElse(null);
		Client client = clientRepo.findByEmail(email).orElse(null);
		
		if(emp!=null) {
			emp.setPassword(passwordEncoder.encode(password));
			empRepo.save(emp);
		}
		else if(client != null) {
			client.setPassword(passwordEncoder.encode(password));
			clientRepo.save(client);
		}
		else {
			throw new RuntimeException("User not found");
		}
		
		//5. mark token used
		tk.setUsed(true);
		tokenRepo.save(tk);
		
		
		return "Password set successfuly";
		
	}
	
	
	@PostMapping("/login/employee")
	public String loginEmployee(@RequestParam String email,
	                            @RequestParam String password) {
	    return employeeService.login(email, password);
	}
	
	

	@PostMapping("/login/client")
	public String loginClient(@RequestParam String email,
	                          @RequestParam String password) {

	    return clientService.login(email, password);
	}
}










