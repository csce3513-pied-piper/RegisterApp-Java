package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class ActiveEmployeeExistsQuery implements VoidCommandInterface{
	
	@Override
	public void execute() throws NotFoundException{
		boolean isActive = true;
		//Query employee database for any active users
		isActive = this.employeeRepository.existsByIsActive(isActive);
		
		if (!isActive) //If no employee is active, throw exception
			throw new NotFoundException("Employee");
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;
}
