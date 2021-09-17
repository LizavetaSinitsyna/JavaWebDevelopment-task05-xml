package by.epamtc.sinitsyna.validation;

import org.junit.After;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class XSDValidatorTest {
	private XSDValidator xsdValidator;
	private InputStream file;

	@Before
	public void init() {
		xsdValidator = XSDValidator.getInstance();
	}

	@Test
	public void testValidateXMLSchemaWithValidFile() throws ValidationException, IOException {
		file = getClass().getClassLoader().getResource("./resource/ValidCandies.xml").openStream();
		Assert.assertTrue(xsdValidator.validateXMLSchema(file));
	}

	@Test
	public void testValidateXMLSchemaWithInvalidFile() throws ValidationException, IOException {
		file = getClass().getClassLoader().getResource("./resource/InvalidCandiesWithNegativeEnergy.xml").openStream();
		Assert.assertFalse(xsdValidator.validateXMLSchema(file));
	}

	@Test(expected = ValidationException.class)
	public void testValidateXMLSchemaByPassingNull() throws ValidationException, IOException {
		xsdValidator.validateXMLSchema(null);
	}

	@After
	public void close() throws IOException {
		if (file != null) {
			file.close();
		}
	}
}
