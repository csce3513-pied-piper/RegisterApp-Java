package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.activeUsers.ActiveUserDeleteCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.commands.products.ProductUpdateCommand;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.repositories.ProductRepository;

public class EmployeeQuery implements ResultCommandInterface<Employee> {
	@Override
	public Employee execute() {
		final Optional<EmployeeEntity> employeeEntity =
			this.employeeRepository.findById(this.employeeId);
		if (employeeEntity.isPresent()) {
			return new Employee(employeeEntity.get());
		} else {
			throw new NotFoundException("Employee");
		}
		
		// Synchronize any incoming changes for UPDATE to the database.
		this.apiEmployee = employeeEntity.get().synchronize(this.apiEmployee);

		// Write, via an UPDATE, any changes to the database.
		this.employeeRepository.save(employeeEntity.get());

		return this.apiEmployee;
	}

	private UUID employeeId;
	public UUID getProductId() {
		return this.employeeId;
	}
	public EmployeeQuery setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	private Employee apiProduct;
	public Employee getApiEmployee() {
		return this.apiEmployee;
	}
	public EmployeeQuery setApiEmployee(final Employee apiEmployee) {
		this.apiEmployee = apiEmployee;
		return this;
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;
}

