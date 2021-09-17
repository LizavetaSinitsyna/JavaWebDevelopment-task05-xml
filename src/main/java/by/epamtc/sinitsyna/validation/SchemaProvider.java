package by.epamtc.sinitsyna.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SchemaProvider {
	private Properties properties;
	private String propertiesFileName;

	private SchemaProvider() {
		properties = new Properties();
		propertiesFileName = "schema.properties";
	}

	private static class SingletonHelper {
		private static final SchemaProvider INSTANCE = new SchemaProvider();
	}

	public static SchemaProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public String retrieveSchemaParam(String paramName) throws IOException {
		String param = null;
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
			properties.load(is);
			param = properties.getProperty(paramName);
		}

		return param;
	}
}
