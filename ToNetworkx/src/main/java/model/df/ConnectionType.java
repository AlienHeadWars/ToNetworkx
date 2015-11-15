package model.df;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConnectionType {
	CLAZZ("class"), FEATURE("feature");

	private final String name;

	@JsonValue
	public String getName() {
		return name;
	}

	private ConnectionType(String name) {
		this.name = name;
	}
}
