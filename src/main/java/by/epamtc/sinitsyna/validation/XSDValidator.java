package by.epamtc.sinitsyna.validation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XSDValidator {
	private String xsdSchemaPath = "/Candies.xsd";

	private XSDValidator() {
	}

	private static class SingletonHelper {
		private static final XSDValidator INSTANCE = new XSDValidator();
	}

	public static XSDValidator getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	public void setXSDSchemaPath(String schemaPath) {
		this.xsdSchemaPath = schemaPath;
	}

	public boolean validateXMLSchema(InputStream fileContent) throws ValidationException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		URL schemaResource = getClass().getClassLoader().getResource(xsdSchemaPath);
		try {
			Schema schema = schemaFactory.newSchema(schemaResource);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new BufferedInputStream(fileContent)));
		} catch (IOException e) {
			throw new ValidationException(e.getMessage(), e);
		} catch (SAXException e) {
			return false;
		}
		return true;
	}

}
