package by.epamtc.sinitsyna.parser.sax;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.epamtc.sinitsyna.parser.AbstractCandiesBuilder;
import by.epamtc.sinitsyna.parser.ParserException;

public class CandiesSAXBuilder extends AbstractCandiesBuilder {
	private CandyHandler candyHandler;
	private SAXParserFactory parserFactory;
	private XMLReader reader;

	public CandiesSAXBuilder() throws ParserException {
		candyHandler = new CandyHandler();

		try {
			parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			reader = parserFactory.newSAXParser().getXMLReader();
			reader.setContentHandler(candyHandler);
		} catch (ParserConfigurationException | SAXException e) {
			throw new ParserException(e.getMessage(), e);
		}
	}

	@Override
	public void buildCandySet(InputStream fileContent) throws ParserException {
		try {
			reader.parse(new InputSource(fileContent));
		} catch (IOException | SAXException e) {
			throw new ParserException(e.getMessage(), e);
		}
		candies = candyHandler.getCandies();
	}

}
