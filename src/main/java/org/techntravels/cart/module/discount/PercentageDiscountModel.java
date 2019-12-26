package org.techntravels.cart.module.discount;

import java.math.BigDecimal;

import org.techntravels.cart.domain.Cart;
import org.techntravels.cart.domain.Product;

/**
 * This class represents percentage type discount model.
 *
 */
public abstract class PercentageDiscountModel extends AbstractDiscountModel {

	protected BigDecimal percentage;

	/**
	 * define percentage to be applied as discount of total effective amount.
	 * 
	 * @param percentage
	 */
	public PercentageDiscountModel(BigDecimal percentage) {
		super();
		this.percentage = percentage;
	}

	/**
	 * True if no percentage type discount has applied in cart yet False if
	 * percentage type discount has already applied
	 * 
	 * @param cart
	 * @return boolean
	 */
	protected boolean isPercentageDiscountApplied(Cart cart) {
		return cart
				.getDiscounts()
				.keySet()
				.stream()
				.anyMatch(
						c -> PercentageDiscountModel.class.isAssignableFrom(c));
	}

	/**
	 * Calculate discount amount based on percentage. But It will not include
	 * Product type GROCERY in total price to calculate discount. If price is
	 * zero after applying existing discount then it will return ZERO.
	 * 
	 * @param cart
	 * @return BigDecimal
	 */
	protected BigDecimal calculateAmount(Cart cart) {
		BigDecimal productPrice = cart.getProducts().stream()
				.filter(p -> p.getType() != Product.Type.GROCERY)
				.map(p -> p.getPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal discountPrice = cart.getDiscounts().entrySet().stream()
				.map(e -> e.getValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal price = productPrice.subtract(discountPrice);
		return price.signum() > 0 ? price.multiply(percentage)
				: BigDecimal.ZERO;
	}
}
