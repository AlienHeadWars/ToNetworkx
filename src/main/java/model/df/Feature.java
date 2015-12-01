package model.df;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Feature {
	private String confirmed;
	private String name;
	private Collection<Connection> outbound;
	private Collection<Connection> inbound;
	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JacksonXmlProperty(localName = "outbound")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Connection> getOutbound() {
		return outbound;
	}
	public void setOutbound(Collection<Connection> outbound) {
		this.outbound = outbound;
	}
	@JacksonXmlProperty(localName = "inbound")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Connection> getInbound() {
		return inbound;
	}
	public void setInbound(Collection<Connection> inbound) {
		this.inbound = inbound;
	}
}
