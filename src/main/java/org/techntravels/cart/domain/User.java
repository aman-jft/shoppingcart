package org.techntravels.cart.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
public class User {
	public enum Type {
		EMPLOYEE, AFFILIATE, REGULAR
	}

	private Type type;
	private String username;
	private LocalDate doj;

}
