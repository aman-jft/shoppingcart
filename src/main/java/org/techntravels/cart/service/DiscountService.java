package org.techntravels.cart.service;

import org.techntravels.cart.module.config.ConfigConstant;
import org.techntravels.cart.module.config.ConfigUtil;
import org.techntravels.cart.module.discount.AffiliateDiscountModel;
import org.techntravels.cart.module.discount.EmployeeDiscountModel;
import org.techntravels.cart.module.discount.IDiscountModel;
import org.techntravels.cart.module.discount.OldCustomerDiscountModel;
import org.techntravels.cart.module.discount.SaleDiscountModel;

public class DiscountService {
	private static IDiscountModel chain;

	public static void load() {
		
		chain = newSaleDisModel();

		OldCustomerDiscountModel oldCustomerDisModel = newOldCustomerDisModel();
		chain.setNextModel(oldCustomerDisModel);

		AffiliateDiscountModel affiliateDisModel = newAffiliateDisModel();
		oldCustomerDisModel.setNextModel(affiliateDisModel);

		EmployeeDiscountModel employeeDisModel = newEmployeeDisModel();
		affiliateDisModel.setNextModel(employeeDisModel);
	}
	
	public static IDiscountModel discountChain() {
		if(chain==null) {
			load();
		}
		return chain;
	}

	public static EmployeeDiscountModel newEmployeeDisModel() {
		return new EmployeeDiscountModel(
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_EMPLOYEE_PERCENTAGE));
	}

	public static AffiliateDiscountModel newAffiliateDisModel() {
		return new AffiliateDiscountModel(
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_AFFILIATE_PERCENTAGE));
	}

	public static OldCustomerDiscountModel newOldCustomerDisModel() {
		return new OldCustomerDiscountModel(
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_OLD_CUSTOMER_PERCENTAGE),
				ConfigUtil
						.getIntValue(ConfigConstant.CART_DISCOUNT_OLD_CUSTOMER_MIN_AGE));
	}

	public static SaleDiscountModel newSaleDisModel() {
		return new SaleDiscountModel(
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_SALES_MINIMUM_BILL),
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_SALES_DISCOUNT_AMOUNT));
	}
}
