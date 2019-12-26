package org.techntravels.cart.module.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.techntravels.cart.domain.Cart;

public class EmployeeDiscountModelTest {
	
	/**
	 * Basic happy path test
	 */
	@Test
	public void test1() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.charger);
		employeeDiscountModel.apply(cart);

		assertNotNull(cart);
		assertNotNull(cart.getProducts());
		assertEquals(cart.getProducts().size(), 1);

		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 1);
		assertTrue(cart.getDiscounts()
				.containsKey(EmployeeDiscountModel.class));
		assertTrue(cart.getDiscounts().get(EmployeeDiscountModel.class)
				.compareTo(new BigDecimal(30.00)) == 0 ? true : false);

		assertTrue(cart.total().compareTo(new BigDecimal(70.00)) == 0 ? true
				: false);
	}
	
	/**
	 * When cart is empty
	 */
	@Test
	public void test2() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		employeeDiscountModel.apply(cart);
		assertNotNull(cart);
		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 0);
		assertTrue(cart.total().compareTo(new BigDecimal(0.00)) == 0 ? true
				: false);
	}

	/**
	 * When user is not employee Type
	 */
	@Test
	public void test3() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.charger);

		employeeDiscountModel.apply(cart);

		assertNotNull(cart);
		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 0);

		assertTrue(cart.total().compareTo(new BigDecimal(100.00)) == 0 ? true
				: false);
	}

	/**
	 * When there is already one PercentageDiscount applied
	 */
	@Test
	public void test4() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.charger);

		HashMap<Class, BigDecimal> discounts = new HashMap<>();
		discounts.put(OldCustomerDiscountModel.class, BigDecimal.TEN);
		cart.setDiscounts(discounts);

		employeeDiscountModel.apply(cart);

		assertTrue(cart.total().compareTo(new BigDecimal(90.00)) == 0 ? true
				: false);
	}

	/**
	 * When there is already one discount applied but non percentage type
	 */
	@Test
	public void test5() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.charger);

		HashMap<Class, BigDecimal> discounts = new HashMap<>();
		discounts.put(SaleDiscountModel.class, BigDecimal.TEN);
		cart.setDiscounts(discounts);

		employeeDiscountModel.apply(cart);

		assertEquals(2, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(63.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart is having only grocery item where discount not applicable
	 */
	@Test
	public void test6() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.oats);

		employeeDiscountModel.apply(cart);

		assertEquals(0, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(50.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart is having grocery product along with other products where
	 * discount applicable
	 */
	@Test
	public void test7() {
		EmployeeDiscountModel employeeDiscountModel = new EmployeeDiscountModel(
				new BigDecimal("0.3"));
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.shoes);
		cart.addProduct(TestSetup.oats);

		employeeDiscountModel.apply(cart);

		assertEquals(1, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(400.00)) == 0 ? true
				: false);
	}
}
