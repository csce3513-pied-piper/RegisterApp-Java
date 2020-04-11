package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntriesQuery implements ResultCommandInterface<Pair<List<TransactionEntryEntity>, List<Product>>> {
    @Override
    public Pair<List<TransactionEntryEntity>, List<Product>> execute() {
        final LinkedList<TransactionEntryEntity> transactionEntryEntities = new LinkedList<TransactionEntryEntity>();
        final LinkedList<Product> products = new LinkedList<Product>();

        for (final TransactionEntryEntity transactionEntryEntity : transactionEntryRepository.findAll()) {
            transactionEntryEntities.addLast(transactionEntryEntity);
            UUID pId = transactionEntryEntity.getProductId();
            final Optional<ProductEntity> productEntity = productRepository.findById(pId);
            if (productEntity.isPresent()) {
                products.addLast(new Product(productEntity.get()));
            } else {
                throw new NotFoundException("Product");
            }
        }

        return new ImmutablePair<>(transactionEntryEntities, products);
    }

    @Autowired
    TransactionEntryRepository transactionEntryRepository;

    @Autowired
    ProductRepository productRepository;
}
