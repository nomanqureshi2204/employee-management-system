package com.noman.ems.service;

import java.util.Map;

public interface AuthService {

	// 🔐 LOGIN WITH JWT + 5 ATTEMPTS

	public Map<String, Object> login(String email, String password);

	// SET PASSWORD USING TOKEN

	public String setPassword(String token, String password);
}