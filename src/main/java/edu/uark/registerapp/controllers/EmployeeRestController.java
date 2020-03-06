package edu.uark.registerapp.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createEmployee(
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		boolean isInitialEmployee = false;
		ApiResponse canCreateEmployeeResponse;

		try {
			// TODO: Query if any active employees exist
			activeUser.execute();
			response.setStatus(HttpServletResponse.SC_FOUND);
			canCreateEmployeeResponse =
				this.redirectUserNotElevated(request, response);
		} catch (final NotFoundException e) {
			isInitialEmployee = true;
			if (isInitialEmployee) {
				/*createdEmployee
					.setRedirectUrl(
						ViewNames.SIGN_IN.getRoute().concat(
							this.buildInitialQueryParameter(
								"employeeId",//QueryParameterNames.EMPLOYEE_ID.getValue(),
								createdEmployee.getEmployeeId())));*/
				createEmployee.setApiEmployee(employee);
			    createEmployee.setEmployeeId(isInitialEmployee);
			    final Employee createdEmployee = createEmployee.execute();
				createdEmployee.setRedirectUrl(ViewNames.SIGN_IN.getRoute());

				return createdEmployee.setIsInitialEmployee(isInitialEmployee);
			}
			response.setStatus(HttpServletResponse.SC_FOUND);
			canCreateEmployeeResponse = new ApiResponse();
			canCreateEmployeeResponse.setRedirectUrl("/");
			return canCreateEmployeeResponse;
		}

		if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return canCreateEmployeeResponse;
		}

		// TODO: Create an employee;
		//final Employee createdEmployee = new Employee();
		/*final Employee createdEmployee = createEmployee.execute();
		createdEmployee.setRedirectUrl(ViewNames.SIGN_IN.getRoute().concat(
				this.buildInitialQueryParameter(
					"employeeId", createdEmployee.getEmployeeId())));*/

		//return createdEmployee.setIsInitialEmployee(isInitialEmployee);
		return canCreateEmployeeResponse;
	}
	@Autowired
	private ActiveEmployeeExistsQuery activeUser;
	@Autowired
	private EmployeeCreateCommand createEmployee;

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
	public @ResponseBody ApiResponse updateEmployee(
		@PathVariable final UUID employeeId,
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(request, response);
		ApiResponse canCreateEmployeeResponse;
		//ActiveEmployeeExistsQuery activeUser = new ActiveEmployeeExistsQuery();

		try {
			// TODO: Query if any active employees exist
			activeUser.execute();
		} catch (final NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			canCreateEmployeeResponse =
					this.redirectUserNotElevated(request, response);
		}
		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		EmployeeUpdateCommand employeeUpdate = new EmployeeUpdateCommand();
		employeeUpdate.setApiEmployee(employee);
		employeeUpdate.setEmployeeId(employeeId);
		employeeUpdate.execute();
		// TODO: Update the employee
		return employee;
	}
	
}