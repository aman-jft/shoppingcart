package org.techntravels.cart.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.techntravels.cart.TestSetup;
import org.techntravels.cart.domain.Cart;
import org.techntravels.cart.module.discount.AffiliateDiscountModel;
import org.techntravels.cart.module.discount.EmployeeDiscountModel;
import org.techntravels.cart.module.discount.OldCustomerDiscountModel;
import org.techntravels.cart.module.discount.SaleDiscountModel;
import org.techntravels.cart.service.DiscountService;

public class DiscountServiceTest {
	
	/**
	 * Create object using service and test calculation
	 */
	@Test
	public void test1() {
		EmployeeDiscountModel employeeDiscountModel = DiscountService.newEmployeeDisModel();
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.charger);
		employeeDiscountModel.apply(cart);
		
		assertEquals(0, cart.total().compareTo(new BigDecimal("70.00")));
	}
	
	@Test
	public void test2() {
		AffiliateDiscountModel affiliateDisModel = DiscountService.newAffiliateDisModel();
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.charger);
		affiliateDisModel.apply(cart);
		
		assertEquals(0, cart.total().compareTo(new BigDecimal("90.00")));
	}
	
	@Test
	public void test3() {
		OldCustomerDiscountModel oldCustomerDisModel = DiscountService.newOldCustomerDisModel();
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		oldCustomerDisModel.apply(cart);
		
		assertEquals(0, cart.total().compareTo(new BigDecimal("95.00")));
	}
	
	@Test
	public void test4() {
		SaleDiscountModel saleDisModel = DiscountService.newSaleDisModel();
		Cart cart = new Cart(TestSetup.regular);
		cart.addProduct(TestSetup.charger);
		saleDisModel.apply(cart);
		
		assertEquals(0, cart.total().compareTo(new BigDecimal("95.00")));
	}
	
	@Test
	public void test5() {
		Cart cart = new Cart(TestSetup.employee);
		cart.addProduct(TestSetup.charger);
		cart.addProduct(TestSetup.shoes);
		cart.addProduct(TestSetup.oats);
		
		cart.checkout();
		
		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 2);
		
		assertTrue(cart.getDiscounts().containsKey(SaleDiscountModel.class));
		assertTrue(cart.getDiscounts().get(SaleDiscountModel.class)
				.compareTo(new BigDecimal(30.00)) == 0 ? true : false);     // 650 -> $5 discount of each $100
		
		assertTrue(cart.getDiscounts()
				.containsKey(EmployeeDiscountModel.class));
		assertEquals(0, cart.getDiscounts().get(EmployeeDiscountModel.class)
				.compareTo(new BigDecimal(171.00)));						// 570 (650(total) - 30(Sale Discount) - 50(grocery amount))-> 570*0.30 -> 171  

		assertEquals(0, cart.total().compareTo(new BigDecimal("449.00")));		// 650 - 30(Sale Discount) - 171(Employee discount)=449
	}
	
	
	@Test
	public void test6() {
		Cart cart = new Cart(TestSetup.affiliate);
		cart.addProduct(TestSetup.charger);
		cart.addProduct(TestSetup.shoes);
		cart.addProduct(TestSetup.oats);
		
		cart.checkout();
		
		assertNotNull(cart.getDiscounts());
		assertEquals(cart.getDiscounts().size(), 2);
		
		assertTrue(cart.getDiscounts().containsKey(SaleDiscountModel.class));
		assertTrue(cart.getDiscounts().get(SaleDiscountModel.class)
				.compareTo(new BigDecimal(30.00)) == 0 ? true : false);     // 650 -> $5 discount of each $100
		
		assertTrue(cart.getDiscounts()
				.containsKey(AffiliateDiscountModel.class));
		assertEquals(0, cart.getDiscounts().get(AffiliateDiscountModel.class)
				.compareTo(new BigDecimal(57.00)));						// 570 (650(total) - 30(Sale Discount) - 50(grocery amount))-> 570*0.10 -> 57 

		assertEquals(0, cart.total().compareTo(new BigDecimal("563.00")));		// 650 - 30(Sale Discount) - 57(Affiliate discount)=563
	}

}
