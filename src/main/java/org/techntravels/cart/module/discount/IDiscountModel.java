package org.techntravels.cart.module.discount;

import org.techntravels.cart.domain.Cart;

public interface IDiscountModel {
	public void setNextModel(IDiscountModel nextModel);
	public void apply(Cart cart);
}
