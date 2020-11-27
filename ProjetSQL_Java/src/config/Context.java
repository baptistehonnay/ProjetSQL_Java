package config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Context {
	private static Properties properties = new Properties();
	
	public static void load(String fileName) {
		Path p = FileSystems.getDefault().getPath(fileName);
		try (InputStream in = Files.newInputStream(p)) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}