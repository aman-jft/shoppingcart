package org.techntravels.cart.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
public class Product {
	public enum Type{
		GROCERY,HARDWARE, FOOTWEAR, ELECTRONICES
	};
	
	private String code;
	private Type type;
	private String name;
	private BigDecimal price;
}
