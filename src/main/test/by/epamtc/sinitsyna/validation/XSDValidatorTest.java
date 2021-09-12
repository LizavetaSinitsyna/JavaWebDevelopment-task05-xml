package by.epamtc.sinitsyna.validation;

import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class XSDValidatorTest {
	private XSDValidator xsdValidator;

	@Before
	public void init() {
		xsdValidator = XSDValidator.getInstance();
		xsdValidator.setXSDSchemaPath(".\\test\\resource\\Candies.xsd");
	}

	@Test
	public void testValidateXMLSchemaWithValidFile() throws FileNotFoundException, ValidationException {
		File file = new File("src\\main\\test\\resource\\ValidCandies.xml");
		InputStream input = new FileInputStream(file);
		Assert.assertTrue(xsdValidator.validateXMLSchema(input));
	}

}
