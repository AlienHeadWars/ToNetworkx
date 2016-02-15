package model.df;

import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Package implements HasName {
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

	@JacksonXmlProperty(localName = "class")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Clazz> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Clazz> classes) {
		this.classes = classes;
	}

	private String confirmed;
	private Collection<Clazz> classes;
}
