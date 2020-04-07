package edu.uark.registerapp.commands.transactions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntryCreateCommand implements ResultCommandInterface<Product> {
    @Override
    public Product execute() {
        final TransactionEntryEntity createdTransactionEntryEntity = this.createTransactionEntryEntity();

        return new Product();
    }

    @Transactional
    private TransactionEntryEntity createTransactionEntryEntity() {
        final Optional<ProductEntity> productEntity =
                this.productRepository.findById(this.productId);
        if (!productEntity.isPresent()) { // No record with the associated record ID exists in the database.
            throw new NotFoundException("Product");
        }

        final List<TransactionEntryEntity> queriedTransactionEntryEntity =
                this.transactionEntryRepository
                        .findByProductId(this.productId);
        if (queriedTransactionEntryEntity.size()>0) {
            // Product ID already defined for another transactionEntry.
            throw new ConflictException("ID");
        }

        // No ENTITY object was returned from the database, thus the Product's
        // ID must be unique.

        // Write, via an INSERT, the new record to the database.
        return this.transactionEntryRepository.save(
                new TransactionEntryEntity(productId));
    }

    // Properties
    private UUID productId;
    public UUID getProductId() {
        return this.productId;
    }
    public TransactionEntryCreateCommand setProductId(final UUID productId) {
        this.productId = productId;
        return this;
    }

    @Autowired
    private TransactionEntryRepository transactionEntryRepository;
    private ProductRepository productRepository;
}
