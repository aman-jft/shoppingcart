package org.techntravels.cart.module.discount;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.techntravels.cart.domain.Cart;

/**
 * Applied given percentage discount if user type is user is older than
 * specified number of years
 */
public class OldCustomerDiscountModel extends PercentageDiscountModel {

	/**
	 * Defines number of years customer should exist to avail this discount.
	 */
	private Integer minAge;

	public OldCustomerDiscountModel(BigDecimal percentage, Integer minAge) {
		super(percentage);
		this.minAge = minAge;
	}

	/**
	 * It check if model is applicable then calculate percentage and set it in
	 * discounts Else it will call next model.
	 * 
	 * @see org.techntravels.cart.module.discount.IDiscountModel#apply(org.techntravels.cart.domain.Cart)
	 */
	public void apply(Cart cart) {
		if (isApplicable(cart)) {
			cart.addDiscount(this.getClass(), calculateAmount(cart));
		} else {
			applyNextRuleIfExist(cart);
		}
	}

	/**
	 * Check if user is older than specified number of years
	 * 
	 * @param cart
	 * @return boolean
	 */
	protected boolean isApplicable(Cart cart) {
		if (super.isPercentageDiscountApplied(cart))
			return false;
		if (cart.total().compareTo(BigDecimal.ZERO) < 1)
			return false;

		return cart.getUser().getDoj()
				.isBefore(LocalDate.now().minusYears(minAge));
	}
}