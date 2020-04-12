package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class TransactionEntryIncrementCommand implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute(){}

    public void plus() {

        final Optional<TransactionEntryEntity> transactionEntryEntity =
                this.transactionEntryRepository.findById(this.transactionEntryId);
        if (!transactionEntryEntity.isPresent()) { // No record with the associated record ID exists in the database.
            throw new NotFoundException("TransactionEntry");
        }

        final Optional<ProductEntity> productEntity =
                this.productRepository.findById(transactionEntryEntity.get().getProductId());
        if (!productEntity.isPresent()) { // No record with the associated record ID exists in the database.
            throw new NotFoundException("Product");
        }

        double pPrice = productEntity.get().getPrice();
        double increasedQuantity = transactionEntryEntity.get().getQuantity() + 1;
        long price = (long)(pPrice * increasedQuantity);
        transactionEntryEntity.get().setQuantity(increasedQuantity);
        transactionEntryEntity.get().setPrice(price);

        // Write, via an UPDATE, any changes to the database.
        this.transactionEntryRepository.save(transactionEntryEntity.get());
    }

    public void minus() {

        final Optional<TransactionEntryEntity> transactionEntryEntity =
                this.transactionEntryRepository.findById(this.transactionEntryId);
        if (!transactionEntryEntity.isPresent()) { // No record with the associated record ID exists in the database.
            throw new NotFoundException("TransactionEntry");
        }

        final Optional<ProductEntity> productEntity =
                this.productRepository.findById(transactionEntryEntity.get().getProductId());
        if (!productEntity.isPresent()) { // No record with the associated record ID exists in the database.
            throw new NotFoundException("Product");
        }

        double pPrice = productEntity.get().getPrice();
        double decreasedQuantity = transactionEntryEntity.get().getQuantity() - 1;
        long price = (long)(pPrice * decreasedQuantity);
        transactionEntryEntity.get().setQuantity(decreasedQuantity);
        transactionEntryEntity.get().setPrice(price);

        // Write, via an UPDATE, any changes to the database.
        this.transactionEntryRepository.save(transactionEntryEntity.get());
    }

    // Properties
    private UUID transactionEntryId;
    public UUID getTransactionEntryId() {
        return this.transactionEntryId;
    }
    public TransactionEntryIncrementCommand setTransactionEntryId(final UUID transactionEntryId) {
        this.transactionEntryId = transactionEntryId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private ProductRepository productRepository;
}
