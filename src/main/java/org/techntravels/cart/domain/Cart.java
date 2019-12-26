package org.techntravels.cart.domain;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.techntravels.cart.module.discount.IDiscountModel;
import org.techntravels.cart.service.DiscountService;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class Cart {
	private User user;
	private List<Product> products = new LinkedList<>();
	private Map<Class, BigDecimal> discounts = new LinkedHashMap<>();

	public Cart(User user) {
		super();
		this.user = user;
	}

	public BigDecimal total() {
		BigDecimal productPrice = products.stream().map(p -> p.getPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal discountPrice = discounts.entrySet().stream()
				.map(e -> e.getValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return productPrice.subtract(discountPrice);
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public void addDiscount(Class className, BigDecimal amount) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) < 1)
			return;
		this.discounts.put(className, amount);
	}
	
	/**
	 * After checkout, it will evaluate discount on products present in cart
	 */
	public void checkout() {
		
		// Calculate Discount
		IDiscountModel chain = DiscountService.discountChain();
		chain.apply(this);
	}
}
