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

import edu.uark.registerapp.commands.transactions.TransactionEntryCreateCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;

@RestController
@RequestMapping(value = "/entity/transactionEntry")
public class SearchRestController extends BaseRestController {
    @RequestMapping(value = "/{productId}", method = RequestMethod.POST)
    public @ResponseBody ApiResponse createProduct(
            @PathVariable final UUID productId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {

        return this.transactionEntryCreateCommand
                .setProductId(productId)
                .execute();
    }

    // Properties
    @Autowired
    private TransactionEntryCreateCommand transactionEntryCreateCommand;
}
