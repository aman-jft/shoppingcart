package org.techntravels.cart.module.discount;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.techntravels.cart.domain.Cart;

public class SaleDiscountModelTest {

	/**
	 * Basic happy path test
	 */
	@Test
	public void test1() {
		SaleDiscountModel saleDiscountModel = new SaleDiscountModel(
				new BigDecimal(100.00), new BigDecimal(5.00));
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		saleDiscountModel.apply(cart);

		assertNotNull(cart);
		assertNotNull(cart.getProducts());
		assertEquals(cart.getProducts().size(), 1);

		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 1);
		assertTrue(cart.getDiscounts().containsKey(SaleDiscountModel.class));
		assertTrue(cart.getDiscounts().get(SaleDiscountModel.class)
				.compareTo(new BigDecimal(5.00)) == 0 ? true : false);

		assertTrue(cart.total().compareTo(new BigDecimal(95.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart is empty
	 */
	@Test
	public void test2() {
		SaleDiscountModel saleDiscountModel = new SaleDiscountModel(
				new BigDecimal(100.00), new BigDecimal(5.00));
		Cart cart = new Cart(TestSetup.regular);
		saleDiscountModel.apply(cart);
		assertTrue(cart.total().compareTo(new BigDecimal(0.00)) == 0 ? true
				: false);
	}

	/**
	 * When threshold amount less then minimum bill amount
	 */
	@Test
	public void test3() {
		SaleDiscountModel saleDiscountModel = new SaleDiscountModel(
				new BigDecimal(100.00), new BigDecimal(5.00));
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.oats);
		saleDiscountModel.apply(cart);
		assertTrue(cart.total().compareTo(TestSetup.oats.getPrice()) == 0 ? true
				: false);
	}

	/**
	 * When cart total is more than threshold amount but less then double of it.
	 */
	@Test
	public void test4() {
		SaleDiscountModel saleDiscountModel = new SaleDiscountModel(
				new BigDecimal(100.00), new BigDecimal(5.00));
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		cart.addProduct(TestSetup.oats);
		saleDiscountModel.apply(cart);
		assertTrue(cart.total().compareTo(new BigDecimal(145.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart total is n times more than threshold amount.
	 */
	@Test
	public void test5() {
		SaleDiscountModel saleDiscountModel = new SaleDiscountModel(
				new BigDecimal(100.00), new BigDecimal(5.00));
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		cart.addProduct(TestSetup.shoes);
		saleDiscountModel.apply(cart);
		assertTrue(cart.total().compareTo(new BigDecimal(570.00)) == 0 ? true
				: false);
	}
}
