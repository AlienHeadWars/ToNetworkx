package converter;

import static java.util.stream.Collectors.toMap;

import java.io.File;

import model.nx.GraphModel;

public class Application {

	private static final String NETWORK_X_OUTPUT_FOLDER = "networkX";
	private static final String DEPENDENCY_FINDER_INPUT_FOLDER = "dependencyFinderOutput";
	private static DependencyFinderXMLLoader dfLoader = new DependencyFinderXMLLoader();
	private static final GraphWriter fileWriter = new GraphWriter(false);
	private static final FileGetter fileGetter = new FileGetter();

	public static void main(String... args) {
		long currentTimeMillis = System.currentTimeMillis();
		fileGetter
				.getFiles(DEPENDENCY_FINDER_INPUT_FOLDER)
				.parallelStream()
				.collect(
						toMap(
								Application::changeToOutputFile,
								file -> new GraphModel(dfLoader.getPackages(file).getPackages())))
				.forEach(fileWriter::writeObject);
		;
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("completed in:" + (endTimeMillis - currentTimeMillis));
	}

	private static File changeToOutputFile(File file) {
		String path = file.getPath();
		String newPath = path.replaceAll(DEPENDENCY_FINDER_INPUT_FOLDER, NETWORK_X_OUTPUT_FOLDER);
		return new File(newPath);
	}
}
