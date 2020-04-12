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

import edu.uark.registerapp.commands.transactions.TransactionEntryClearCommand;
import edu.uark.registerapp.commands.transactions.TransactionEntryIncrementCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;

@RestController
@RequestMapping(value = "/entity/transactionEntry")
public class TransactionRestController extends BaseRestController {
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public @ResponseBody ApiResponse clearTransactionEntry(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {

        this.transactionEntryClearCommand
                .execute();

        return new ApiResponse();
    }

    @RequestMapping(value = "/plus/{transactionEntryId}", method = RequestMethod.PUT)
    public @ResponseBody ApiResponse plusTransactionEntry(
            @PathVariable final UUID transactionEntryId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {

        this.transactionEntryIncrementCommand
                .setTransactionEntryId(transactionEntryId)
                .plus();

        return new ApiResponse();
    }

    @RequestMapping(value = "/minus/{transactionEntryId}", method = RequestMethod.PUT)
    public @ResponseBody ApiResponse minusTransactionEntry(
            @PathVariable final UUID transactionEntryId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {

        this.transactionEntryIncrementCommand
                .setTransactionEntryId(transactionEntryId)
                .minus();

        return new ApiResponse();
    }

    @RequestMapping(value = "/delete/{transactionEntryId}", method = RequestMethod.DELETE)
    public @ResponseBody ApiResponse deleteTransactionEntry(
            @PathVariable final UUID transactionEntryId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {

        this.transactionEntryClearCommand
                .setTransactionEntryId(transactionEntryId)
                .delete();

        return new ApiResponse();
    }

    @RequestMapping(value = "/save/{cashierId}/{transactionReferenceId}", method = RequestMethod.POST)
    public @ResponseBody ApiResponse createTransaction(
            @PathVariable final UUID cashierId,
            @PathVariable final UUID transactionReferenceId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {

        this.transactionEntryClearCommand
                .setCashierId(cashierId)
                .setTransactionReferenceId(transactionReferenceId)
                .save();

        return new ApiResponse();
    }

    // Properties
    @Autowired
    private TransactionEntryClearCommand transactionEntryClearCommand;

    @Autowired
    private TransactionEntryIncrementCommand transactionEntryIncrementCommand;
}
