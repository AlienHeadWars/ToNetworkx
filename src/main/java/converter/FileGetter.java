package converter;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class FileGetter {

	ClassLoader classLoader = FileGetter.class.getClassLoader();

	public Collection<File> getFiles(String fileDirectory) {
		return FileUtils
				.listFiles(new File(classLoader.getResource(fileDirectory).getFile()), null, false);
	}

}
