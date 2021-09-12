package by.epamtc.sinitsyna.parser.sax;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.epamtc.sinitsyna.bean.Candy;
import by.epamtc.sinitsyna.bean.NutritionalValue;
import by.epamtc.sinitsyna.bean.PackedCandy;
import by.epamtc.sinitsyna.parser.CandyXMLElementEnum;
import by.epamtc.sinitsyna.parser.GlazeProvider;
import by.epamtc.sinitsyna.validation.CandyValidationHelper;
import by.epamtc.sinitsyna.validation.ValidationException;

public class CandyHandler extends DefaultHandler {
	private static final String UNKNOWN_ATTRIBUTE_MESSAGE = "Unknown attribute type!";
	private static final String UNKNOWN_ELEMENT_MESSAGE = "Unknown element type!";

	private Set<Candy> candies;
	private Candy currentCandy;
	private CandyXMLElementEnum currentCandyEnum;
	private NutritionalValue currentValue;
	private Map<String, Integer> ingredients;
	private int currentIngredientAmount;
	private EnumSet<CandyXMLElementEnum> elementsWithText;

	public CandyHandler() {
		candies = new TreeSet<>();
		elementsWithText = EnumSet.range(CandyXMLElementEnum.NAME, CandyXMLElementEnum.CARBOHYDRATES);
	}

	public Set<Candy> getCandies() {
		return candies;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try {
			CandyXMLElementEnum elementName = CandyXMLElementEnum.valueOf(localName.toUpperCase());

			switch (elementName) {
			case CANDY:
				currentCandy = new Candy();
				readCandyAttributes(currentCandy, attributes);
				break;
			case PACKED_CANDY:
				currentCandy = new PackedCandy();
				readCandyAttributes(currentCandy, attributes);
				break;
			case VALUE:
				currentValue = new NutritionalValue();
				break;
			case INGREDIENTS:
				ingredients = new HashMap<>();
				break;
			case INGREDIENT:
				try {
					currentIngredientAmount = Integer.parseInt(attributes.getValue(0));
					if (currentIngredientAmount < 0) {
						throw new ValidationException(CandyValidationHelper.NON_VALID_INGREDIENT_AMOUNT_MESSAGE);
					}
				} catch (NumberFormatException | ValidationException e) {
					throw new SAXException(e.getMessage(), e);
				}
				currentCandyEnum = elementName;
				break;
			default:
				if (elementsWithText.contains(elementName)) {
					currentCandyEnum = elementName;
				}
				break;
			}
		} catch (IllegalArgumentException e) {
			throw new SAXException(UNKNOWN_ELEMENT_MESSAGE, e);
		}

	}

	private void readCandyAttributes(Candy candy, Attributes attributes) throws SAXException {
		try {
			for (int i = 0; i < attributes.getLength(); i++) {
				CandyXMLElementEnum attribute = CandyXMLElementEnum.valueOf(attributes.getLocalName(i).toUpperCase());
				switch (attribute) {
				case ID:
					candy.setId(attributes.getValue(i));
					break;
				case GLAZE:
					candy.setGlaze(GlazeProvider.getInstance().getGlazeType(attributes.getValue(i)));
					break;
				case PRODUCER:
					candy.setProducer(attributes.getValue(i));
					break;
				default:
					throw new SAXException(UNKNOWN_ATTRIBUTE_MESSAGE);
				}
			}
		} catch (IllegalArgumentException e) {
			throw new SAXException(UNKNOWN_ATTRIBUTE_MESSAGE, e);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		String s = new String(ch, start, length).trim();
		if (currentCandyEnum != null) {
			switch (currentCandyEnum) {
			case NAME:
				currentCandy.setName(s);
				break;
			case ENERGY:
				try {
					int energy = Integer.parseInt(s);
					if (energy < 0) {
						throw new ValidationException(CandyValidationHelper.NON_VALID_ENERGY_MESSAGE);
					}
					currentCandy.setEnergy(energy);
				} catch (NumberFormatException | ValidationException e) {
					throw new SAXException(e.getMessage(), e);
				}
				break;
			case PRODUCTION_DATE_TIME:
				try {
					currentCandy.setProductionDateTime(LocalDateTime.parse(s));
				} catch (DateTimeParseException e) {
					throw new SAXException(e.getMessage(), e);
				}
				break;
			case INGREDIENT:
				ingredients.put(s, currentIngredientAmount);
				break;
			case FATS:
				try {
					currentValue.setFats(Double.parseDouble(s));
				} catch (NumberFormatException e) {
					throw new SAXException(e.getMessage(), e);
				}
				break;
			case PROTAINS:
				try {
					currentValue.setProtains(Double.parseDouble(s));
				} catch (NumberFormatException e) {
					throw new SAXException(e.getMessage(), e);
				}
				break;
			case CARBOHYDRATES:
				try {
					currentValue.setCarbohydrates(Double.parseDouble(s));
				} catch (NumberFormatException e) {
					throw new SAXException(e.getMessage(), e);
				}
				break;
			case FILLING:
				currentCandy.setFilling(s);
				break;
			case PACK:
				((PackedCandy) currentCandy).setPack(s);
				break;
			default:
				throw new SAXException(UNKNOWN_ELEMENT_MESSAGE);
			}
			currentCandyEnum = null;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		try {
			CandyXMLElementEnum elementName = CandyXMLElementEnum.valueOf(localName.toUpperCase());

			switch (elementName) {
			case CANDY:
				candies.add(currentCandy);
				currentCandy = null;
				break;
			case PACKED_CANDY:
				candies.add(currentCandy);
				currentCandy = null;
				break;
			case VALUE:
				try {
					if (!CandyValidationHelper.isNutritionalValueValid(currentValue)) {
						throw new ValidationException(CandyValidationHelper.NON_VALID_NUTRITION_VALUE_MESSAGE);
					}
				} catch (ValidationException e) {
					throw new SAXException(e.getMessage(), e);
				}
				currentCandy.setNutritionalValue(currentValue);
				currentValue = null;
				break;
			case INGREDIENTS:
				currentCandy.setIngredients(ingredients);
				ingredients = null;
				break;
			default:
				break;
			}
		} catch (IllegalArgumentException e) {
			throw new SAXException(UNKNOWN_ELEMENT_MESSAGE);
		}
	}
}
