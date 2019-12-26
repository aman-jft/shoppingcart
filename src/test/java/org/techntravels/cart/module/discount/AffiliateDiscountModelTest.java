package org.techntravels.cart.module.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.techntravels.cart.TestSetup;
import org.techntravels.cart.domain.Cart;

public class AffiliateDiscountModelTest {
	/**
	 * Basic happy path test
	 */
	@Test
	public void test1() {
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.charger);
		affiliateDiscountModel.apply(cart);

		assertNotNull(cart);
		assertNotNull(cart.getProducts());
		assertEquals(cart.getProducts().size(), 1);

		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 1);
		assertTrue(cart.getDiscounts()
				.containsKey(AffiliateDiscountModel.class));
		assertTrue(cart.getDiscounts().get(AffiliateDiscountModel.class)
				.compareTo(new BigDecimal(10.00)) == 0 ? true : false);

		assertTrue(cart.total().compareTo(new BigDecimal(90.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart is empty
	 */
	@Test
	public void test2() {
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.affiliate);
		affiliateDiscountModel.apply(cart);
		assertNotNull(cart);
		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 0);
		assertTrue(cart.total().compareTo(new BigDecimal(0.00)) == 0 ? true
				: false);
	}

	/**
	 * When user is not Affiliate Type
	 */
	@Test
	public void test3() {
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);

		affiliateDiscountModel.apply(cart);

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
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.charger);

		cart.addDiscount(OldCustomerDiscountModel.class, BigDecimal.ONE);

		affiliateDiscountModel.apply(cart);

		assertTrue(cart.total().compareTo(new BigDecimal(99.00)) == 0 ? true
				: false);
	}

	/**
	 * When there is already one discount applied but non percentage type
	 */
	@Test
	public void test5() {
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.charger);

		cart.addDiscount(SaleDiscountModel.class, BigDecimal.TEN);

		affiliateDiscountModel.apply(cart);

		assertEquals(2, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(81.00)) == 0 ? true
				: false);
	}

	/**
	 * When cart is having only grocery item where discount not applicable
	 */
	@Test
	public void test6() {
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.oats);

		affiliateDiscountModel.apply(cart);

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
		AffiliateDiscountModel affiliateDiscountModel = new AffiliateDiscountModel(
				new BigDecimal("0.1"));
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.shoes);
		cart.addProduct(TestSetup.oats);

		affiliateDiscountModel.apply(cart);

		assertEquals(1, cart.getDiscounts().size());
		assertTrue(cart.total().compareTo(new BigDecimal(500.00)) == 0 ? true
				: false);
	}
}
