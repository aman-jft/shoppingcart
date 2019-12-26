package org.techntravels.cart.module.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.techntravels.cart.TestSetup;
import org.techntravels.cart.domain.Cart;

public class OldCustomerDiscountModelTest {

	/**
	 * Basic happy path test
	 */
	@Test
	public void test1() {
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 2);
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		oldCustomerDiscountModel.apply(cart);

		assertNotNull(cart);
		assertNotNull(cart.getProducts());
		assertEquals(cart.getProducts().size(), 1);

		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 1);
		assertTrue(cart.getDiscounts().containsKey(
				OldCustomerDiscountModel.class));
		assertTrue(cart.getDiscounts().get(OldCustomerDiscountModel.class)
				.compareTo(new BigDecimal(5.00)) == 0 ? true : false);

		assertTrue(cart.total().compareTo(new BigDecimal(95.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart is empty
	 */
	@Test
	public void test2() {
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 2);
		Cart cart = new Cart(TestSetup.regular);
		oldCustomerDiscountModel.apply(cart);
		assertNotNull(cart);
		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 0);
		assertTrue(cart.total().compareTo(new BigDecimal(0.00)) == 0 ? true
				: false);
	}

	/**
	 * When user is not that old to avail discount
	 */
	@Test
	public void test3() {
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 4);
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);

		oldCustomerDiscountModel.apply(cart);

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
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 2);
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);

		cart.addDiscount(EmployeeDiscountModel.class, BigDecimal.TEN);

		oldCustomerDiscountModel.apply(cart);

		assertTrue(cart.total().compareTo(new BigDecimal(90.00)) == 0 ? true
				: false);
	}

	/**
	 * When there is already one discount applied but non percentage type
	 */
	@Test
	public void test5() {
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 2);
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		
		cart.addDiscount(SaleDiscountModel.class, BigDecimal.TEN);

		oldCustomerDiscountModel.apply(cart);

		assertEquals(2, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(85.5)) == 0 ? true
				: false);
	}

	/**
	 * When cart is having only grocery item where discount not applicable
	 */
	@Test
	public void test6() {
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 2);
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.oats);

		oldCustomerDiscountModel.apply(cart);

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
		OldCustomerDiscountModel oldCustomerDiscountModel = new OldCustomerDiscountModel(
				new BigDecimal("0.05"), 2);
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.shoes);
		cart.addProduct(TestSetup.oats);

		oldCustomerDiscountModel.apply(cart);

		assertEquals(1, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(525.00)) == 0 ? true
				: false);
	}
}
