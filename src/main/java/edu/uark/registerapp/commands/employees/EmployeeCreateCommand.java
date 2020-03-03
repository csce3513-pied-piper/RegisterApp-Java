package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class EmployeeCreateCommand implements ResultCommandInterface<Employee>{
	@Override
	public Employee execute() {
		this.validateProperties();
		if(this.apiEmployee.getIsInitialEmployee()) {
			this.apiEmployee.setClassification(-1);//not sure what int represents general manager
		}

		final EmployeeEntity createdEmployeeEntity = this.employeeRepository.save(new EmployeeEntity(apiEmployee));
		
		// Map employee entity to api object
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
