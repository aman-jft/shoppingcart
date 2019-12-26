package org.techntravels.cart.module.config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class ConfigUtil {
	private static Properties properties;
	public static final String CONFIG_FILE_NAME = "config.properties";

	static {
		try (InputStream input = ConfigUtil.class.getClassLoader()
				.getResourceAsStream(CONFIG_FILE_NAME)) {
			properties = new Properties();
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Error loading configuration.");
		}
	}

	public static String getValue(ConfigConstant configConstant) {
		if (properties.containsKey(configConstant.getValue())) {
			return properties.getProperty(configConstant.getValue());
		} else {
			throw new IllegalArgumentException(
					"Property not found in config file: "
							+ configConstant.getValue());
		}
	}

	public static BigDecimal getDecimalValue(ConfigConstant configConstant) {
		String value = getValue(configConstant);
		try {
			return new BigDecimal(value);
		} catch (ArithmeticException | NumberFormatException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(
					"Property value is not valid. Provide decimal value: "
							+ configConstant.getValue());
		}
	}

	public static Integer getIntValue(ConfigConstant configConstant) {
		String value = getValue(configConstant);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(
					"Property value is not valid. Provide decimal value: "
							+ configConstant.getValue());
		}
	}
}
