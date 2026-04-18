package com.noman.ems.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	public void sendEmail(String to,String link) {
		System.out.println("Email send  to:"+to);
		System.out.println("Click here: "+link);
	}
}
