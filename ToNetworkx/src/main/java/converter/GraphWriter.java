package converter;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static utilties.ExceptionHandling.runTimeExceptionise;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import model.df.Clazz;
import model.nx.GraphModel;

public class GraphWriter {

	private Predicate<Clazz> clazzFilter;

	public GraphWriter(Boolean includeNonConfirmed) {
		clazzFilter = (clazz) -> includeNonConfirmed || "yes".equals(clazz.getConfirmed());
	}

	public void writeObject(File file, GraphModel object) {

		runTimeExceptionise(() -> {
			file.createNewFile();
			Map<String, Collection<String>> edges = getEdges(object);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			Consumer<String> write = (string) -> runTimeExceptionise(() -> bw.write(string));
			edges.entrySet().forEach(entry -> {
				write.accept(entry.getKey());
				entry.getValue().forEach(name -> write.accept(" " + name));
				write.accept("\n");
			});

			bw.close();
		});
	}

	public Map<String, Collection<String>> getEdges(GraphModel object) {
		return object
				.getEdges()
				.entrySet()
				.stream()
				.filter(e -> clazzFilter.test(e.getKey()))
				.collect(
						toMap(
								entry -> entry.getKey().getName(),
								entry -> entry
										.getValue()
										.stream()
										.filter(clazzFilter)
										.filter(clazz->clazz!=entry.getKey())
										.map(clazz -> clazz.getName())
										.collect(toList())));
	}
}
