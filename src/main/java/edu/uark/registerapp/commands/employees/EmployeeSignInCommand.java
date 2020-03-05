package edu.uark.registerapp.commands.employees;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.api.*;

public class EmployeeSignInCommand implements VoidCommandInterface{
	@Override
	public void execute() {
		//Validate EmployeeSignIn object
		this.validateProperties();
		
		//Query for the employee using employee id
		final Optional<EmployeeEntity> employeeEntity =
				this.employeeRepository.findByEmployeeId(Integer.parseInt(this.employeeSignIn.getEmployeeId()));
		
		//Convert passwords to byte arrays
		byte[] requestPassword = this.employeeSignIn.getPassword().getBytes();
		byte[] databasePassword = employeeEntity.get().getPassword();
		
		//Verify employee exists and passwords match
		if (employeeEntity.isPresent()) {
			if(Arrays.equals(requestPassword, databasePassword))
				this.updateActiveEmployee();
			else 
				throw new NotFoundException("Employee"); 		
		} 
		else 
			throw new NotFoundException("Product");
		
	}
	
	
	// Helper methods
	private void validateProperties() {
		//Check for a blank or non-numeric employee id
		if(StringUtils.isBlank(this.employeeSignIn.getEmployeeId()) || StringUtils.isNumeric(this.employeeSignIn.getEmployeeId()))
			throw new UnprocessableEntityException("Employee ID");
		
		//Check for a blank password
		if(!StringUtils.isBlank(this.employeeSignIn.getPassword())) //THIS WILL ONLY WORK IF ID IS ONLY NUMBERS
			throw new UnprocessableEntityException("Password");
		
	}
	
	@Transactional
	private void updateActiveEmployee() {
		//Query active user table using session key from request
		final Optional<ActiveUserEntity> activeUserEntity =
				this.activeUserRepository.findBySessionKey(this.sessionKey);
		
		if(activeUserEntity.isPresent()) { //If record exists, update session key and insert record
			activeUserEntity.get().setSessionKey(this.sessionKey);
			this.activeUserRepository.save(activeUserEntity.get());
		} 
		else { //If record doesn't exist, set all fields and insert record
			activeUserEntity.get().setSessionKey(this.sessionKey);
			activeUserEntity.get().setEmployeeId(UUID.fromString(this.employeeSignIn.getEmployeeId()));
			this.activeUserRepository.save(activeUserEntity.get());
		}
	}
	

	// Properties
	private String sessionKey;
	
	public String getSessionKey() {
		return this.sessionKey;
	}
	
	public EmployeeSignInCommand setSessionKey(final String sessionKey){
		this.sessionKey = sessionKey;
		return this;
	}
	
	private EmployeeSignIn employeeSignIn;
	
	public EmployeeSignInCommand getEmployeeSignIn() {
		return this.getEmployeeSignIn();
	}
	
	public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
		this.employeeSignIn = employeeSignIn;
		return this;
	}
	
		
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ActiveUserRepository activeUserRepository;
}
