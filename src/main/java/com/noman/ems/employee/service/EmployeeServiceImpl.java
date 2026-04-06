package com.noman.ems.employee.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noman.ems.common.entity.Token;
import com.noman.ems.common.repository.TokenRepository;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// generate Employee Id
	private String generateEmployeeId() {
		// fetchin last employee with highest id
		Employee lastEmployee = employeeRepository.findTopByOrderByIdDesc().orElse(null);

		// agar koi employee nahinhai
		if (lastEmployee == null) {
			return "JTC-001";
		}

		// if last employee id (example: JTC-005)
		String lastId = lastEmployee.getEmployeeId();

		// extract number
		int num = Integer.parseInt(lastId.split("-")[1]);

		num++; // next number

		// format: JTC-006
		return String.format("JTC-%03d", num);
	}

	// add employee
	@Override
	public Employee addEmployee(Employee employee) {

		// custom employee id generate
		employee.setEmployeeId(generateEmployeeId());

		// set current date
		employee.setDateOfJoining(LocalDate.now());

		// employee save
		Employee savedEmployee = employeeRepository.save(employee);

		// token generate

		String tokenValue = UUID.randomUUID().toString();

		Token token = Token.builder().email(savedEmployee.getEmail()) // kis email ke liye token
				.token(tokenValue) // unique token
				.expiryTime(LocalDateTime.now().plusMinutes(5)) // 5 min expiry
				.used(false) // abhi use nahi hua
				.build();

	}
}
