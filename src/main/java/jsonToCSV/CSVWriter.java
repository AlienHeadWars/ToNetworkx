package jsonToCSV;

import static utilties.ExceptionHandling.runTimeExceptionise;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.nx.GraphModel;

public class CSVWriter {

	public void writeObject(File file, Map<String,Collection> object) {
	
		runTimeExceptionise(() -> {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			Consumer<String> write = (string) -> runTimeExceptionise(() -> bw.write(string));
			object.entrySet().forEach(entry -> {
				write.accept(entry.getKey());
				entry.getValue().forEach(value -> write.accept("," + value));
				write.accept("\n");
			});
			bw.close();
		});
	}
	
}
