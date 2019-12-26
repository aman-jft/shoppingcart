package org.techntravels.cart;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;
import java.util.function.Function;

import org.techntravels.cart.domain.Cart;
import org.techntravels.cart.domain.Product;
import org.techntravels.cart.domain.User;

public class Runner {
	Cart cart;
	static Scanner sc = new Scanner(System.in);
	static Function<Product, String> formatter = (p) -> {
		return String.format("%s (%s) - $%.2f", p.getName(), p.getType().toString(), p.getPrice().doubleValue());
	};
	
	public static void main(String[] args) {
		Runner run = new Runner();

		User user = run.getUser();
		run.cart = new Cart(user);
		boolean exist = false;
		do {
			Product p = run.addProduct();
			if (p != null) {
				run.cart.addProduct(p);
			} else {
				exist = true;
			}
		} while (!exist);

		run.cart.checkout();
		run.displayCart();
	}

	public Product addProduct() {
		Product charger = Product.builder().code("101").type(Product.Type.ELECTRONICES).name("Charger")
				.price(new BigDecimal(30.00)).build();

		Product shoes = Product.builder().code("102").type(Product.Type.FOOTWEAR).name("Running Shoes")
				.price(new BigDecimal(50.00)).build();

		Product oats = Product.builder().code("103").type(Product.Type.GROCERY).name("Oats")
				.price(new BigDecimal(20.00)).build();
		
		System.out.println("Select option:");
		System.out.println(" 1. " + formatter.apply(charger));
		System.out.println(" 2. " + formatter.apply(shoes));
		System.out.println(" 3. " + formatter.apply(oats));
		System.out.println(" 4. Checkout");

		int i = sc.nextInt();
		Product current = null;
		switch (i) {
		case 1:
			current = charger;
			break;
		case 2:
			current = shoes;
			break;
		case 3:
			current = oats;
			break;
		case 4:
			current = null;
			break;
		default:
			System.out.println("Select again!!!");
			current = addProduct();
		}
		return current;

	}

	public User getUser() {
		System.out.println("Select User:");
		System.out.println("1. Employee");
		System.out.println("2. Affiliate");
		System.out.println("3. Regular");

		int i = sc.nextInt();
		User current = null;
		switch (i) {
		case 1:
			current = User.builder().type(User.Type.EMPLOYEE).username("Employee")
					.doj(LocalDate.of(2019, Month.DECEMBER, 1)).build();
			break;
		case 2:
			current = User.builder().type(User.Type.AFFILIATE).username("AFFILIATE")
					.doj(LocalDate.of(2019, Month.DECEMBER, 1)).build();
			break;
		case 3:
			current = User.builder().type(User.Type.REGULAR).username("REGULAR")
					.doj(LocalDate.of(2017, Month.DECEMBER, 1)).build();
			break;
		default:
			System.out.println("Select again!!!");
			current = getUser();
		}
		return current;
	}

	public void displayCart() {
		System.out.println("################################");
		System.out.println(String.format("%10s", "")+"No. of Product in Cart: " + cart.getProducts().size());
		
		cart.getProducts().stream().forEach(p -> {
			System.out.println(String.format("%10s", "")+formatter.apply(p));
			
		});
		
		System.out.println("");
		
		cart.getDiscounts().entrySet().stream().forEach(p -> System.out.println(String.format(String.format("%10s", "")+"%s - %.2f", p.getKey().getSimpleName(), p.getValue().doubleValue())));
		
		System.out.println("");
		System.out.println(String.format("%10sTotal - %.2f ", "", cart.total().doubleValue()));
		System.out.println("################################");
	}
}
