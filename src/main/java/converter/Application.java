package converter;

import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.util.Optional;

import model.nx.GraphModel;

public class Application {

	private static final String NETWORK_X_OUTPUT_FOLDER = "networkX";
	private static final String DEPENDENCY_FINDER_INPUT_FOLDER = "dependencyFinderOutput";
	private static DependencyFinderXMLLoader dfLoader = new DependencyFinderXMLLoader();
	private static final GraphWriter fileWriter = new GraphWriter(false);
	private static final FileGetter fileGetter = new FileGetter();

	public static void main(String... args) {
		long currentTimeMillis = System.currentTimeMillis();
		fileGetter.getFiles(DEPENDENCY_FINDER_INPUT_FOLDER).stream().forEach(file -> {
			GraphModel graphModel = new GraphModel(dfLoader.getPackages(file).getPackages());
			fileWriter.writeObject(
					changeToOutputFile(file, Optional.empty()),
					graphModel.getClazzEdges());
			fileWriter.writeObject(
					changeToOutputFile(file, Optional.of("_packages")),
					graphModel.getPackageEdges());
			fileWriter.writeObject(
					changeToOutputFile(file, Optional.of("_features")),
					graphModel.getFeatureEdges());
		});
		;
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("completed in:" + (endTimeMillis - currentTimeMillis));
	}

	private static File changeToOutputFile(File file, Optional<String> extension) {
		String path = file.getPath();
		String newPath =
				path.replaceAll(DEPENDENCY_FINDER_INPUT_FOLDER, NETWORK_X_OUTPUT_FOLDER)
						+ extension.orElse("");
		return new File(newPath);
	}
}
