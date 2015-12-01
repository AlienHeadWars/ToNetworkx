package model.df;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Dependencies {
	private Collection<Package> packages;

//	@JacksonXmlElementWrapper(localName="package")
//	@XmlElement(name="package")
//	@JsonProperty("package")
	@JacksonXmlProperty(localName = "package")
    @JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Package> getPackages() {
		return packages;
	}

	
	public void setPackages(Collection<Package> packages) {
		this.packages = packages;
	}
}
