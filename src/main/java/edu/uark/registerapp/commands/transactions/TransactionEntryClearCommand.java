package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class TransactionEntryClearCommand implements VoidCommandInterface {
    @Override
    public void execute() {
        this.transactionEntryRepository.deleteAll();
    }

    public void delete(){
        this.transactionEntryRepository.deleteById(transactionEntryId);
    }

    public void save(){
        long total = 0;
        for(final TransactionEntryEntity transactionEntryEntity: transactionEntryRepository.findAll()){
            total = total + transactionEntryEntity.getPrice();
            ProductEntity productEntity = productRepository.findById(transactionEntryEntity.getProductId()).get();
            productEntity.setCount(productEntity.getCount() - (int)transactionEntryEntity.getQuantity());
            productRepository.save(productEntity);
        }

        transactionRepository.save(
                new TransactionEntity(this.cashierId, total, 1, this.transactionReferenceId));

        this.execute();
    }

    private UUID transactionEntryId;
    public UUID getTransactionEntryId() {
        return this.transactionEntryId;
    }
    public TransactionEntryClearCommand setTransactionEntryId(final UUID transactionEntryId) {
        this.transactionEntryId = transactionEntryId;
        return this;
    }

    private UUID cashierId;
    public UUID getCashierId() {
        return this.cashierId;
    }
    public TransactionEntryClearCommand setCashierId(final UUID cashierId) {
        this.cashierId = cashierId;
        return this;
    }

    private UUID transactionReferenceId;
    public UUID getTransactionReferenceId() {
        return this.transactionReferenceId;
    }
    public TransactionEntryClearCommand setTransactionReferenceId(final UUID transactionReferenceId) {
        this.transactionReferenceId = transactionReferenceId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    ProductRepository productRepository;
}