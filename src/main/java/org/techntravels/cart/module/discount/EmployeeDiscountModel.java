package org.techntravels.cart.module.discount;

import java.math.BigDecimal;

import org.techntravels.cart.domain.Cart;
import org.techntravels.cart.domain.User;

/**
 * Applied given percentage discount if user type is EMPLOYEE
 */
public class EmployeeDiscountModel extends PercentageDiscountModel {

	public EmployeeDiscountModel(BigDecimal percentage) {
		super(percentage);
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
	 * Check if user type is EMPLOYEE. Also, no percentage type has already
	 * applied in cart yet.
	 * 
	 * @param cart
	 * @return boolean
	 */
	protected boolean isApplicable(Cart cart) {
		return !super.isPercentageDiscountApplied(cart)
				&& cart.getUser().getType() == User.Type.EMPLOYEE ? true
				: false;
	}
}
