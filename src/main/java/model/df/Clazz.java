package model.df;

import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Clazz implements Node {
	private String confirmed;
	private Collection<Connection> outBound;
	private Collection<Connection> inBound;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	@JacksonXmlProperty(localName = "outbound")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Connection> getOutBound() {
		return outBound;
	}

	public void setOutBound(Collection<Connection> outBound) {
		this.outBound = outBound;
	}

	@JacksonXmlProperty(localName = "inbound")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Connection> getInBound() {
		return inBound;
	}

	public void setInBound(Collection<Connection> inBound) {
		this.inBound = inBound;
	}

	@JacksonXmlProperty(localName = "feature")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Collection<Feature> features) {
		this.features = features;
	}

	private Collection<Feature> features;
}
