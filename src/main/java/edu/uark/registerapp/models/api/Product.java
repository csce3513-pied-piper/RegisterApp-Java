package edu.uark.registerapp.models.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.models.entities.ProductEntity;

public class Product extends ApiResponse {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public Product setId(final UUID id) {
		this.id = id;
		return this;
	}

	private String lookupCode;

	public String getLookupCode() {
		return this.lookupCode;
	}

	public Product setLookupCode(final String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int count;

	public int getCount() {
		return this.count;
	}

	public Product setCount(final int count) {
		this.count = count;
		return this;
	}

	private long price;

	public long getPrice() {
		return this.price;
	}

	public Product setPrice(final long price) {
		this.price = price;
		return this;
	}

	private String createdOn;

	public String getCreatedOn() {
		return this.createdOn;
	}

	public Product setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Product setCreatedOn(final LocalDateTime createdOn) {
		this.createdOn =
			createdOn.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

		return this;
	}

	public Product() {
		super();

		this.count = -1;
		this.id = new UUID(0, 0);
		this.lookupCode = StringUtils.EMPTY;

		this.setCreatedOn(LocalDateTime.now());
	}

	private String stock;
	private String dollars;

	public Product(final ProductEntity productEntity) {
		super(false);

		this.id = productEntity.getId();
		this.count = productEntity.getCount();
		this.stock = "In stock: ";
		this.lookupCode = productEntity.getLookupCode();
		this.price = productEntity.getPrice();
		BigDecimal payment = new BigDecimal(this.price).movePointLeft(2);
		this.dollars = "$";
		this.setCreatedOn(productEntity.getCreatedOn());
	}
}
