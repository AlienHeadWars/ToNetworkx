package commit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import converter.FileGetter;

public class CommitSelector {

	private final static String PATH = "CommitInformation/jetty.txt";
	private final static ClassLoader classLoader = CommitSelector.class.getClassLoader();

	public static void main(String[] args) throws Exception {

		try (
				BufferedReader br =
						new BufferedReader(
								new FileReader(
										new File(classLoader.getResource(PATH).getFile())))) {

			Collection<String> commits = readFile(br);
			commits.forEach(System.out::println);
		}
	}

	private static Collection<String> readFile(BufferedReader br) throws IOException {
		String sCurrentLine = br.readLine();
		AtomicReference<String> currentCommit = new AtomicReference<String>("");
		String version = sCurrentLine;
		Collection<AtomicReference<String>> commits = new ArrayList<>();
		while ((sCurrentLine = br.readLine()) != null) {
			if (sCurrentLine.startsWith("jetty-")) {
				version = sCurrentLine;
			} else if (version.startsWith("jetty-7.")) {
				if (sCurrentLine.startsWith(" + ")) {
					currentCommit = new AtomicReference<String>(version+sCurrentLine);
					commits.add(currentCommit);
				} else {
					final String update = sCurrentLine;
					currentCommit.updateAndGet(old -> old + " " + update);
				}
			}
		}
		return commits.stream().map(AtomicReference::get).collect(Collectors.toList());
	}

}
