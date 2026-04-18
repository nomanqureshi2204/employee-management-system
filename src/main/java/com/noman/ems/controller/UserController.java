//package com.noman.ems.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.noman.ems.entity.User;
//import com.noman.ems.repository.UserRepository;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//	
//	@Autowired
//	private UserRepository userRepo;
//	
//	@PostMapping
//	public User createUser(@RequestBody User user) {
//		return userRepo.save(user);
//	}
//}
//
//
//
//
//
//
//
//
//
//
