package edu.uark.registerapp.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.transactions.TransactionEntriesQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/transactionMenu")
public class TransactionRouteController extends BaseRouteController{
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTransactionListing(
			@RequestParam final Map<String, String> queryParameters,
			final HttpServletRequest request
		) {
		final Optional<ActiveUserEntity> activeUserEntity =
				this.getCurrentUser(request);
		if (!activeUserEntity.isPresent()) {
			return buildInvalidSessionResponse();
		}

		ModelAndView modelAndView =
				this.setErrorMessageFromQueryString(
						new ModelAndView(ViewNames.TRANSACTION.getViewName()),
						queryParameters);

		modelAndView.addObject(
				ViewModelNames.IS_ELEVATED_USER.getValue(),
				this.isElevatedUser(activeUserEntity.get()));

		Pair<List<TransactionEntryEntity>, List<Product>> p = transactionEntriesQuery.execute();

		modelAndView.addObject(
				ViewModelNames.TRANSACTION_ENTRIES.getValue(),
				p.getKey());

		modelAndView.addObject(
				ViewModelNames.PRODUCTS.getValue(),
				p.getValue());

		return modelAndView;
	}

	// Properties
	@Autowired
	private TransactionEntriesQuery transactionEntriesQuery;
}
