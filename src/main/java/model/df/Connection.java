package model.df;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Connection {

	private ConnectionType type;
	private String confirmed;
	private String value;

	public ConnectionType getType() {
		return type;
	}

	public void setType(ConnectionType type) {
		this.type = type;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	@JacksonXmlText
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
