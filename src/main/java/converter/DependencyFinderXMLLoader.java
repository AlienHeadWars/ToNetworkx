package converter;

import static utilties.ExceptionHandling.runTimeExceptionise;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import model.df.Dependencies;

public class DependencyFinderXMLLoader {

	XmlMapper mapper = new XmlMapper();

	public Dependencies getPackages(File file) {
		// mapper.read
		return runTimeExceptionise(
				() -> mapper.readValue(file, Dependencies.class));
	}
}
