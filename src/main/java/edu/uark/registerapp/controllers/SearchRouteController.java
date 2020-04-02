package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductByLookupCodeQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/search/lookupcode={lookupCode}")
public class SearchRouteController extends BaseRouteController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showProductListing(
            @RequestParam final Map<String, String> queryParameters,
            final HttpServletRequest request,
            @PathVariable String lookupCode) {

        final Optional<ActiveUserEntity> activeUserEntity =
                this.getCurrentUser(request);
        if (!activeUserEntity.isPresent()) {
            return buildInvalidSessionResponse();
        }

        ModelAndView modelAndView =
                this.setErrorMessageFromQueryString(
                        new ModelAndView(ViewNames.SEARCH.getViewName()),
                        queryParameters);

        modelAndView.addObject(
                ViewModelNames.IS_ELEVATED_USER.getValue(),
                this.isElevatedUser(activeUserEntity.get()));

        try {
            this.productsQuery.setLookupCode(lookupCode);
            modelAndView.addObject(
                    ViewModelNames.PRODUCTS.getValue(),
                    this.productsQuery.find());
        } catch (final Exception e) {
            modelAndView.addObject(
                    ViewModelNames.ERROR_MESSAGE.getValue(),
                    e.getMessage());
            modelAndView.addObject(
                    ViewModelNames.PRODUCTS.getValue(),
                    (new Product[0]));
        }

        return modelAndView;
    }

    // Properties
    @Autowired
    private ProductByLookupCodeQuery productsQuery;
}
