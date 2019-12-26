package org.techntravels.cart.module.discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.techntravels.cart.domain.Product;
import org.techntravels.cart.domain.User;

public class TestSetup {
	public static final Product charger;
	public static final Product shoes;
	public static final Product oats;

	public static User employee;
	public static User affiliate;
	public static User regular;

	static {
		charger = Product.builder().code("101").type(Product.Type.ELECTRONICES)
				.name("Charger").price(new BigDecimal(100.00)).build();

		shoes = Product.builder().code("102").type(Product.Type.FOOTWEAR)
				.name("Running Shoes").price(new BigDecimal(500.00)).build();

		oats = Product.builder().code("103").type(Product.Type.GROCERY)
				.name("Oats").price(new BigDecimal(50.00)).build();

		employee = User.builder().type(User.Type.EMPLOYEE).username("Employee")
				.doj(LocalDate.of(2019, Month.DECEMBER, 1)).build();

		affiliate = User.builder().type(User.Type.AFFILIATE)
				.username("AFFILIATE")
				.doj(LocalDate.of(2017, Month.DECEMBER, 1)).build();

		regular = User.builder().type(User.Type.REGULAR).username("REGULAR")
				.doj(LocalDate.of(2017, Month.DECEMBER, 1)).build();
	}
}
