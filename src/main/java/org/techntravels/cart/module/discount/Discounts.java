package org.techntravels.cart.module.discount;

import org.techntravels.cart.module.config.ConfigConstant;
import org.techntravels.cart.module.config.ConfigUtil;

public class Discounts {

	public static void load() {
		SaleDiscountModel saleDisModel = newSaleDisModel();

		OldCustomerDiscountModel oldCustomerDisModel = newOldCustomerDisModel();
		saleDisModel.setNextModel(oldCustomerDisModel);

		AffiliateDiscountModel affiliateDisModel = newAffiliateDisModel();
		oldCustomerDisModel.setNextModel(affiliateDisModel);

		EmployeeDiscountModel employeeDisModel = newEmployeeDisModel();
		affiliateDisModel.setNextModel(employeeDisModel);
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
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_AFFILIATE_PERCENTAGE),
				ConfigUtil
						.getIntValue(ConfigConstant.CART_DISCOUNT_AFFILIATE_PERCENTAGE));
	}

	public static SaleDiscountModel newSaleDisModel() {
		return new SaleDiscountModel(
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_SALES_MINIMUM_BILL),
				ConfigUtil
						.getDecimalValue(ConfigConstant.CART_DISCOUNT_SALES_DISCOUNT_AMOUNT));
	}
}
