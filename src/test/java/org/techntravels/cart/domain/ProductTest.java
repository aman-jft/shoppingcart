package org.techntravels.cart.domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ProductTest {
	@Test
	public void test1() {
		Product product = Product.builder().code("101").type(Product.Type.ELECTRONICES)
		.name("Charger").price(new BigDecimal(100.00)).build();
		
		assertEquals("Charger", product.getName());
		assertEquals("101", product.getCode());
		assertEquals(Product.Type.ELECTRONICES, product.getType());
		assertEquals(0, product.getPrice().compareTo(new BigDecimal(100.00)));
	}
}
