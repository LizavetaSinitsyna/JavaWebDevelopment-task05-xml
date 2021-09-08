package by.epamtc.sinitsyna.parser.sax;

import java.time.LocalDateTime;
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

public class CandyHandler extends DefaultHandler {
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
		CandyXMLElementEnum elementName = CandyXMLElementEnum.valueOf(localName.toUpperCase());
		if (elementName != null) {
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
				currentIngredientAmount = Integer.parseInt(attributes.getValue(0));
				currentCandyEnum = elementName;
				break;
			default:
				if (elementsWithText.contains(elementName)) {
					currentCandyEnum = elementName;
				}
				break;
			}
		}
	}

	private void readCandyAttributes(Candy candy, Attributes attributes) throws SAXException {
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
				throw new SAXException("Unknown attribute type!");
			}
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
				currentCandy.setEnergy(Integer.parseInt(s));
				break;
			case PRODUCTION_DATE_TIME:
				currentCandy.setProductionDateTime(LocalDateTime.parse(s));
				break;
			case INGREDIENT:
				ingredients.put(s, currentIngredientAmount);
				break;
			case FATS:
				currentValue.setFats(Double.parseDouble(s));
				break;
			case PROTAINS:
				currentValue.setProtains(Double.parseDouble(s));
				break;
			case CARBOHYDRATES:
				currentValue.setCarbohydrates(Double.parseDouble(s));
				break;
			case FILLING:
				currentCandy.setFilling(s);
				break;
			case PACK:
				((PackedCandy) currentCandy).setPack(s);
				break;
			default:
				throw new SAXException("Unknown attribute type!");
			}
			currentCandyEnum = null;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		CandyXMLElementEnum elementName = CandyXMLElementEnum.valueOf(localName.toUpperCase());;
		if (elementName != null) {
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
		}
	}
}
