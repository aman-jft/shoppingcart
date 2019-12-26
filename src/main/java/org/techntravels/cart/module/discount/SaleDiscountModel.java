package org.techntravels.cart.module.discount;

import java.math.BigDecimal;

import org.techntravels.cart.domain.Cart;

/**
 * If bill is above specified amount then discount will be at rate of minimum
 * amount of total bill.
 *
 */
public class SaleDiscountModel extends AbstractDiscountModel {
	private BigDecimal minimumBill;
	private BigDecimal discountAmount;

	public SaleDiscountModel(BigDecimal minimumBill, BigDecimal discountAmount) {
		super();
		this.minimumBill = minimumBill;
		this.discountAmount = discountAmount;
	}

	/**
	 * It check if model is applicable then calculate discount and set it in
	 * discounts. Always call next model is exist.
	 * 
	 * @see org.techntravels.cart.module.discount.IDiscountModel#apply(org.techntravels.cart.domain.Cart)
	 */
	public void apply(Cart cart) {
		if (isApplicable(cart)) {
			cart.addDiscount(this.getClass(), calculateAmount(cart));
		}
		applyNextRuleIfExist(cart);
	}

	/**
	 * Check if amount is less then minimum amount for discount
	 * 
	 * @param cart
	 * @return boolean
	 */
	protected boolean isApplicable(Cart cart) {
		return cart.total().compareTo(minimumBill) >= 0 ? true : false;
	}

	/**
	 * Calculate discount based on minimum limit.
	 * 
	 * @param cart
	 * @return BigDecimal
	 */
	protected BigDecimal calculateAmount(Cart cart) {
		long rate = cart.total().divide(minimumBill).longValue();
		return discountAmount.multiply(new BigDecimal(rate));
	}
}
