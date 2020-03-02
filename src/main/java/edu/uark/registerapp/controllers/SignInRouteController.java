package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.api.Product;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	// TODO: Route for initial page load
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(@RequestParam Map<String,String> allParams) {
		ActiveEmployeeExistsQuery querySearch = new ActiveEmployeeExistsQuery();
		try {
			querySearch.execute(); 
		}
		catch(NotFoundException e){
		/*int x = 1;
		if(x == 1) {*/
			return (new ModelAndView("redirect:/employeeDetail"));
		}
			return (new ModelAndView("signIn"));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(EmployeeSignIn employeeSignIn, HttpServletRequest request)
		// TODO: Define an object that will represent the sign in request and add it as a parameter here
	 {
		EmployeeSignInCommand signInCommand = new EmployeeSignInCommand();
		try {
			signInCommand.execute();
		}
		catch(NotFoundException e) {
			System.out.println("Sign In was not successfull");
			return (new ModelAndView("signIn"));
		}
		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user
		signInCommand.setEmployeeSignIn(employeeSignIn);
		signInCommand.setSessionKey(request.getRequestedSessionId());
		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}
}
