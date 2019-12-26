package org.techntravels.cart.module.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;

public class ConfigTest {
	@Test
	public void test1() {
		String value = ConfigUtil
				.getValue(ConfigConstant.CART_DISCOUNT_EMPLOYEE_PERCENTAGE);
		assertEquals("0.30", value);

		value = ConfigUtil
				.getValue(ConfigConstant.CART_DISCOUNT_AFFILIATE_PERCENTAGE);
		assertEquals("0.10", value);

		value = ConfigUtil
				.getValue(ConfigConstant.CART_DISCOUNT_OLD_CUSTOMER_PERCENTAGE);
		assertEquals("0.05", value);

		value = ConfigUtil
				.getValue(ConfigConstant.CART_DISCOUNT_OLD_CUSTOMER_MIN_AGE);
		assertEquals("2", value);

		value = ConfigUtil
				.getValue(ConfigConstant.CART_DISCOUNT_SALES_MINIMUM_BILL);
		assertEquals("100.00", value);

		value = ConfigUtil
				.getValue(ConfigConstant.CART_DISCOUNT_SALES_DISCOUNT_AMOUNT);
		assertEquals("5.00", value);
	}

	@Test
	public void test2() {
		BigDecimal value = ConfigUtil
				.getDecimalValue(ConfigConstant.CART_DISCOUNT_EMPLOYEE_PERCENTAGE);
		assertEquals(0, value.compareTo(new BigDecimal("0.30")));

		value = ConfigUtil
				.getDecimalValue(ConfigConstant.CART_DISCOUNT_AFFILIATE_PERCENTAGE);
		assertEquals(0, value.compareTo(new BigDecimal("0.10")));

		value = ConfigUtil
				.getDecimalValue(ConfigConstant.CART_DISCOUNT_OLD_CUSTOMER_PERCENTAGE);
		assertEquals(0, value.compareTo(new BigDecimal("0.05")));

		value = ConfigUtil
				.getDecimalValue(ConfigConstant.CART_DISCOUNT_SALES_MINIMUM_BILL);
		assertEquals(0, value.compareTo(new BigDecimal("100.00")));

		value = ConfigUtil
				.getDecimalValue(ConfigConstant.CART_DISCOUNT_SALES_DISCOUNT_AMOUNT);
		assertEquals(0, value.compareTo(new BigDecimal("5.00")));
	}

	@Test
	public void test3() {
		Integer value = ConfigUtil
				.getIntValue(ConfigConstant.CART_DISCOUNT_OLD_CUSTOMER_MIN_AGE);
		assertTrue(value == 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test4() {
		
		ConfigConstant mock = PowerMockito.spy(ConfigConstant.CART_DISCOUNT_EMPLOYEE_PERCENTAGE);
		when(mock.getValue()).thenReturn("jibber jabber");
		String value = ConfigUtil.getValue(mock);
		fail("Should throw exception");
	}
}
