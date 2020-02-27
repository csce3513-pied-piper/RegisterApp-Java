package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

public class ActiveUserDeleteCommand implements VoidCommandInterface{
	@Transactional
	@Override
	public void execute() {
		//Validate incomming employee object
		this.validateEmployeeRequest();
		
		//Query active user table using session key
		final Optional<ActiveUserEntity> activeUserEntity =
				this.activeUserRepository.findBySessionKey(this.sessionKey);
		
		if(activeUserEntity.isPresent()) //If active user exists, delete record
			this.activeUserRepository.delete(activeUserEntity.get());
		
		else //If not, throw exception
			throw new NotFoundException("Active User");	
	}
	
	//Helper methods
	public void validateEmployeeRequest() {
		if(StringUtils.isBlank(employee.getFirstName()) || StringUtils.isBlank(employee.getLastName())) 
			throw new UnprocessableEntityException("Name");
	}
	
	
	// Properties
	private String sessionKey;
	
	public String getSessionKey() {
		return this.sessionKey;
	}
	
	public ActiveUserDeleteCommand setSessionKey(final String sessionKey){
		this.sessionKey = sessionKey;
		return this;
	}
	
	private Employee employee;
	
	public Employee getEmployee() {
		return this.employee;
	}
	
	public ActiveUserDeleteCommand setEmployee(final Employee employee) {
		this.employee = employee;
		return this;
	}
		
	@Autowired
	private ActiveUserRepository activeUserRepository;
}
