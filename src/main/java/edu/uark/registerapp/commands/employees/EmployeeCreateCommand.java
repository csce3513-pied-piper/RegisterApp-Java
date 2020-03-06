package edu.uark.registerapp.commands.employees;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee>{
	@Override
	public Employee execute() {
		this.validateProperties();
		if(this.apiEmployee.getIsInitialEmployee()) {
			this.apiEmployee.setClassification(-1);//not sure what int represents general manager
		}
		
		//Insert new employee entity
		final EmployeeEntity createdEmployeeEntity = this.employeeRepository.save(new EmployeeEntity(apiEmployee));
		
		//Synchronize entity to api object for return
		this.apiEmployee.setId(createdEmployeeEntity.getId()); 
		this.apiEmployee.setCreatedOn(createdEmployeeEntity.getCreatedOn());

		return this.apiEmployee;
	}
	
	// Helper methods
	private void validateProperties() {
		if ((StringUtils.isBlank(this.apiEmployee.getFirstName())) || 
				(StringUtils.isBlank(this.apiEmployee.getLastName())) ||
				(StringUtils.isBlank(this.apiEmployee.getPassword()))) {
			throw new UnprocessableEntityException("Employee");
		}
	}
	
	//Properties
	public boolean getIsInitialEmployee() {
		return this.apiEmployee.getIsInitialEmployee();
	}
	public EmployeeCreateCommand setEmployeeId(final boolean isInitialEmployee) {
		this.apiEmployee.setIsInitialEmployee(isInitialEmployee);
		return this;
	}

	private Employee apiEmployee;
	public Employee getApiEmployee() {
		return this.apiEmployee;
	}
	public EmployeeCreateCommand setApiEmployee(final Employee apiEmployee) {
		this.apiEmployee = apiEmployee;
		return this;
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;
}
