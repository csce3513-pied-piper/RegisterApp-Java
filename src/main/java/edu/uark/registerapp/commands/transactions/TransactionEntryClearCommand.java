package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntryClearCommand implements VoidCommandInterface {
    @Override
    public void execute() {
        this.transactionEntryRepository.deleteAll();
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
}
