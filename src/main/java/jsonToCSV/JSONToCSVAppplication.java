package jsonToCSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import converter.DependencyFinderXMLLoader;
import converter.FileGetter;
import converter.GraphWriter;
import model.nx.GraphModel;
import static utilties.ExceptionHandling.runTimeExceptionise;

public class JSONToCSVAppplication {

	private static final String CSV_FOLDER = "CSV";
	private static final String JSON_INPUT_FOLDER = "JSON";
	private static final CSVWriter fileWriter = new CSVWriter();
	private static final FileGetter fileGetter = new FileGetter();
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static JavaType mapType =
			objectMapper.getTypeFactory().constructMapType(
					HashMap.class,
					String.class,
					Collection.class);

	public static void main(String... args) {
		long currentTimeMillis = System.currentTimeMillis();
		fileGetter.getFiles(JSON_INPUT_FOLDER).stream().forEach(
				file -> runTimeExceptionise(
						() -> fileWriter.writeObject(
								changeToOutputFile(file),
								objectMapper.convertValue(objectMapper.readTree(file), mapType))));

		;
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("completed in:" + (endTimeMillis - currentTimeMillis));
	}

	private static File changeToOutputFile(File file) {
		String path = file.getPath();
		String newPath = path.replaceAll(JSON_INPUT_FOLDER, CSV_FOLDER) + ".csv";
		return new File(newPath);
	}

}
