package org.techntravels.cart.module.discount;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.techntravels.cart.domain.Cart;

public class BasicCalculationTest {




	

	
	@Test
	public void employeeModelTest() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.charger);
		employeeDiscountModel.apply(cart);
		assertTrue(cart.total().compareTo(new BigDecimal(70.00))==0?true:false);
	}
}
