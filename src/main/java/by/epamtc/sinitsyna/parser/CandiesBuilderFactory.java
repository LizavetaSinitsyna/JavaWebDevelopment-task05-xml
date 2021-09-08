package by.epamtc.sinitsyna.parser;

import by.epamtc.sinitsyna.parser.dom.CandiesDOMBuilder;
import by.epamtc.sinitsyna.parser.sax.CandiesSAXBuilder;
import by.epamtc.sinitsyna.parser.stax.CandiesStAXBuilder;

public class CandiesBuilderFactory {
	private static final String NULL_PARSER_EXCEPTION_MESSAGE = "Parser type can't be null.";

	private enum ParserType {
		DOM, SAX, STAX
	}

	private CandiesBuilderFactory() {

	}

	private static class SingletonHelper {
		private static final CandiesBuilderFactory INSTANCE = new CandiesBuilderFactory();
	}

	public static CandiesBuilderFactory getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public AbstractCandiesBuilder createCandiesBuilder(String parserType) throws ParserException {
		if (parserType == null) {
			throw new ParserException(NULL_PARSER_EXCEPTION_MESSAGE);
		}
		ParserType type = ParserType.valueOf(parserType.toUpperCase());
		switch (type) {
		case DOM:
			return new CandiesDOMBuilder();
		case SAX:
			return new CandiesSAXBuilder();
		case STAX:
			return new CandiesStAXBuilder();
		default:
			return null;
		}
	}

}
