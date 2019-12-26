package org.techntravels.cart.module.discount;

import org.techntravels.cart.domain.Cart;

public abstract class AbstractDiscountModel implements IDiscountModel {

	IDiscountModel nextModel;

	@Override
	public void setNextModel(IDiscountModel nextModel) {
		this.nextModel = nextModel;
	}

	public void applyNextRuleIfExist(Cart cart) {
		if (this.nextModel != null) {
			this.nextModel.apply(cart);
		}
		return;
	}
}
