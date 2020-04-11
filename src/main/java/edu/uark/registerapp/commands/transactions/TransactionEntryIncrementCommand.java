package edu.uark.registerapp.commands.transactions;

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
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;

@Service
public class TransactionEntryIncrementCommand implements ResultCommandInterface<Product>{
	@Override
    public Product execute() {
        final TransactionEntryEntity updatedTransactionEntryEntity = this.incrementTransactionEntryEntity();
    }

@Transactional
public TransactionEntryEntity incrementTransactionEntryEntity() {
	final Optional<TransactionEntryEntity> transactionEntryEntity =
            this.transactionEntryRepository.findById(this.productId);
	
	if (!transactionEntryEntity.isPresent()) { // No record with the associated record ID exists in the database.
        throw new NotFoundException("TransactionEntry");
    }
	
	//Get quantity and increment by one
	double newQuantity = transactionEntryEntity.get().getQuantity() + 1;
	transactionEntryEntity.get().setQuantity(newQuantity);
	
	// Write, via an UPDATE, any changes to the database.
	this.transactionEntryRepository.save(transactionEntryEntity.get());

}


//Properties
private UUID productId;
public UUID getProductId() {
    return this.productId;
}
public TransactionEntryIncrementCommand setProductId(final UUID productId) {
    this.productId = productId;
    return this;
}

@Autowired
private TransactionEntryRepository transactionEntryRepository;
}