package by.epamtc.sinitsyna.parser.stax;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.epamtc.sinitsyna.bean.Candy;
import by.epamtc.sinitsyna.bean.NutritionalValue;
import by.epamtc.sinitsyna.bean.PackedCandy;
import by.epamtc.sinitsyna.parser.AbstractCandiesBuilder;
import by.epamtc.sinitsyna.parser.CandyXMLElementEnum;
import by.epamtc.sinitsyna.parser.GlazeProvider;
import by.epamtc.sinitsyna.parser.ParserException;
import by.epamtc.sinitsyna.validation.CandyValidationHelper;
import by.epamtc.sinitsyna.validation.ValidationException;

public class CandiesStAXBuilder extends AbstractCandiesBuilder {
	private XMLInputFactory inputFactory;

	public CandiesStAXBuilder() {
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public void buildCandySet(InputStream fileContent) throws ParserException {
		if (fileContent == null) {
			throw new ParserException(NULL_FILE_EXCEPTION);
		}
		try {
			XMLStreamReader xmlReader;
			CandyXMLElementEnum name = null;

			int type;
			xmlReader = inputFactory.createXMLStreamReader(fileContent);
			while (xmlReader.hasNext()) {
				type = xmlReader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = CandyXMLElementEnum.valueOf(xmlReader.getLocalName().toUpperCase());
					if (name == CandyXMLElementEnum.CANDY) {
						Candy candy = new Candy();
						buildCandy(xmlReader, candy);
						candies.add(candy);
					} else if (name == CandyXMLElementEnum.PACKED_CANDY) {
						Candy packedCandy = new PackedCandy();
						buildCandy(xmlReader, packedCandy);
						candies.add(packedCandy);
					}
				}
			}
		} catch (XMLStreamException | IllegalArgumentException e) {
			throw new ParserException(e.getMessage(), e);
		}

	}

	private void buildCandy(XMLStreamReader xmlReader, Candy candy) throws ParserException {
		CandyXMLElementEnum name;
		int type;

		candy.setId(xmlReader.getAttributeValue(null, CandyXMLElementEnum.ID.getValue()));
		candy.setProducer(xmlReader.getAttributeValue(null, CandyXMLElementEnum.PRODUCER.getValue()));
		candy.setGlaze(GlazeProvider.getInstance()
				.getGlazeType(xmlReader.getAttributeValue(null, CandyXMLElementEnum.GLAZE.getValue())));

		try {
			while (xmlReader.hasNext()) {
				type = xmlReader.next();
				switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					name = CandyXMLElementEnum.valueOf(xmlReader.getLocalName().toUpperCase());
					switch (name) {
					case NAME:
						candy.setName(getText(xmlReader));
						break;
					case ENERGY:
						int energy = Integer.parseInt(getText(xmlReader));
						if (energy < 0) {
							throw new ValidationException(CandyValidationHelper.NON_VALID_ENERGY_MESSAGE);
						}
						candy.setEnergy(energy);
						break;
					case PRODUCTION_DATE_TIME:
						try {
							candy.setProductionDateTime(LocalDateTime.parse(getText(xmlReader)));
						} catch (DateTimeParseException e) {
							throw new ParserException(e.getMessage(), e);
						}
						break;
					case INGREDIENTS:
						candy.setIngredients(buildIngredients(xmlReader));
						break;
					case VALUE:
						NutritionalValue value = buildValue(xmlReader);
						if (!CandyValidationHelper.isNutritionalValueValid(value)) {
							throw new ValidationException(CandyValidationHelper.NON_VALID_NUTRITION_VALUE_MESSAGE);
						}
						candy.setNutritionalValue(value);
						break;
					case FILLING:
						candy.setFilling(getText(xmlReader));
						break;
					case PACK:
						String pack = getText(xmlReader);
						((PackedCandy) candy).setPack(pack);
						break;
					default:
						break;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					name = CandyXMLElementEnum.valueOf(xmlReader.getLocalName().toUpperCase());
					if (name == CandyXMLElementEnum.CANDY || name == CandyXMLElementEnum.PACKED_CANDY) {
						return;
					}
					break;
				default:
					break;
				}
			}
		} catch (XMLStreamException | IllegalArgumentException | ValidationException e) {
			throw new ParserException(e.getMessage(), e);
		}

	}

	private NutritionalValue buildValue(XMLStreamReader xmlReader) throws XMLStreamException {
		NutritionalValue value = new NutritionalValue();
		int type;
		String name;

		while (xmlReader.hasNext()) {
			type = xmlReader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = xmlReader.getLocalName().toUpperCase();
				switch (CandyXMLElementEnum.valueOf(name)) {
				case FATS:
					value.setFats(Double.parseDouble(getText(xmlReader)));
					break;
				case PROTAINS:
					value.setProtains(Double.parseDouble(getText(xmlReader)));
					break;
				case CARBOHYDRATES:
					value.setCarbohydrates(Double.parseDouble(getText(xmlReader)));
					break;
				default:
					break;

				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = xmlReader.getLocalName().toUpperCase();
				if (CandyXMLElementEnum.valueOf(name) == CandyXMLElementEnum.VALUE) {
					return value;
				}
				break;

			default:
				break;
			}
		}
		return value;
	}

	private Map<String, Integer> buildIngredients(XMLStreamReader xmlReader)
			throws XMLStreamException, ValidationException {
		String ingredientName = null;
		int amount = 0;
		int type;
		Map<String, Integer> ingredients = new HashMap<>();

		while (xmlReader.hasNext()) {
			type = xmlReader.next();
			if (type == XMLStreamConstants.START_ELEMENT) {
				amount = Integer.parseInt(xmlReader.getAttributeValue(null, CandyXMLElementEnum.AMOUNT.getValue()));
				if (amount < 0) {
					throw new ValidationException(CandyValidationHelper.NON_VALID_INGREDIENT_AMOUNT_MESSAGE);
				}
				ingredientName = getText(xmlReader);
			} else if (type == XMLStreamConstants.END_ELEMENT) {
				switch (CandyXMLElementEnum.valueOf(xmlReader.getLocalName().toUpperCase())) {
				case INGREDIENT:
					ingredients.put(ingredientName, amount);
					break;
				case INGREDIENTS:
					return ingredients;
				default:
					break;
				}
			}
		}
		return ingredients;
	}

	private String getText(XMLStreamReader xmlReader) throws XMLStreamException {
		String text = null;
		if (xmlReader.hasNext()) {
			xmlReader.next();
			text = xmlReader.getText();
		}
		return text;
	}

}
