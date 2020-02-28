package edu.uark.registerapp.models.api;

//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.models.entities.EmployeeEntity;

public class EmployeeSignIn {
	
	private String employeeId;
	public String getEmployeeId() {
		return this.employeeId;
	}
	public Employee setEmployeeId(final int employeeId) {
		this.employeeId = EmployeeHelper.padEmployeeId(employeeId);
		return this;
	}
	public Employee setEmployeeId(final String employeeId) {
		this.employeeId = employeeId;
		return this;
	}
	
	private String password;
	public String getPassword() {
		return this.password;
	}
	public Employee setPassword(final String password) {
		this.password = password;
		return this;
	}
	
	public EmployeeSignIn() {
		this.employeeId = StringUtils.EMPTY;
		this.password = StringUtils.EMPTY;
	}
	
	public EmployeeSignIn(final EmployeeEntity employeeEntity) {
		this.employeeId = EmployeeHelper.padEmployeeId(employeeEntity.getEmployeeId());
		this.password = StringUtils.EMPTY;
	}
}