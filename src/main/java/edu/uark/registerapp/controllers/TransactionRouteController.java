package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewNames;

@Controller
@RequestMapping(value = "/transactionMenu")
public class TransactionRouteController extends BaseController{
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTransactionListing(
			@RequestParam final Map<String, String> queryParameters,
			final HttpServletRequest request
		) {
		ModelAndView modelAndView = new ModelAndView(ViewNames.TRANSACTION.getViewName());
		return modelAndView;
	}
}
