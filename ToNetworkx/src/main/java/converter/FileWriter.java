package converter;

import static utilties.ExceptionHandling.runTimeExceptionise;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class FileWriter {

    ObjectMapper mapper = new ObjectMapper();

	{
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public void writeObject(File file, Object object) {

		runTimeExceptionise(() -> {
			file.createNewFile();
			mapper.writeValue(file, object);
		});
	}

}
