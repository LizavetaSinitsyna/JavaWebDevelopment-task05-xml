package by.epamtc.sinitsyna.parser.dom;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.epamtc.sinitsyna.bean.Candy;
import by.epamtc.sinitsyna.bean.PackedCandy;
import by.epamtc.sinitsyna.bean.NutritionalValue;
import by.epamtc.sinitsyna.parser.AbstractCandiesBuilder;
import by.epamtc.sinitsyna.parser.CandyXMLElementEnum;
import by.epamtc.sinitsyna.parser.GlazeProvider;
import by.epamtc.sinitsyna.parser.ParserException;
import by.epamtc.sinitsyna.validation.CandyValidationHelper;
import by.epamtc.sinitsyna.validation.ValidationException;

public class CandiesDOMBuilder extends AbstractCandiesBuilder {
	private DocumentBuilder docBuilder;

	public CandiesDOMBuilder() throws ParserException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ParserException(e.getMessage(), e);
		}

	}

	@Override
	public void buildCandySet(InputStream fileContent) throws ParserException {
		if (fileContent == null) {
			throw new ParserException(NULL_FILE_EXCEPTION);
		}
		try {
			Document doc = docBuilder.parse(fileContent);
			Element root = doc.getDocumentElement();
			NodeList candiesList = root.getElementsByTagName(CandyXMLElementEnum.CANDY.getValue());
			for (int i = 0; i < candiesList.getLength(); i++) {
				Element element = (Element) candiesList.item(i);
				Candy candy = new Candy();
				buildCandy(element, candy);
				candies.add(candy);
			}

			NodeList packedCandiesList = root.getElementsByTagName(CandyXMLElementEnum.PACKED_CANDY.getValue());
			for (int i = 0; i < packedCandiesList.getLength(); i++) {
				Element element = (Element) packedCandiesList.item(i);
				PackedCandy packedCandy = new PackedCandy();
				buildCandy(element, packedCandy);
				candies.add(packedCandy);
			}
		} catch (SAXException | IOException e) {
			throw new ParserException(e.getMessage(), e);
		}

	}

	private void buildCandy(Element element, Candy candy) throws ParserException {
		int energy;
		NutritionalValue value = new NutritionalValue();

		candy.setId(element.getAttribute(CandyXMLElementEnum.ID.getValue()));
		candy.setProducer(element.getAttribute(CandyXMLElementEnum.PRODUCER.getValue()));
		candy.setGlaze(
				GlazeProvider.getInstance().getGlazeType(element.getAttribute(CandyXMLElementEnum.GLAZE.getValue())));
		candy.setName(retrieveElementTextContent(element, CandyXMLElementEnum.NAME.getValue()));
		try {
			energy = Integer.parseInt(retrieveElementTextContent(element, CandyXMLElementEnum.ENERGY.getValue()));
			if (energy < 0) {
				throw new ValidationException(CandyValidationHelper.NON_VALID_ENERGY_MESSAGE);
			}
			candy.setEnergy(energy);
			candy.setProductionDateTime(LocalDateTime
					.parse(retrieveElementTextContent(element, CandyXMLElementEnum.PRODUCTION_DATE_TIME.getValue())));
			candy.setIngredients(buildIngredients(element));
			value.setCarbohydrates(Double
					.parseDouble(retrieveElementTextContent(element, CandyXMLElementEnum.CARBOHYDRATES.getValue())));
			value.setFats(Double.parseDouble(retrieveElementTextContent(element, CandyXMLElementEnum.FATS.getValue())));
			value.setProtains(
					Double.parseDouble(retrieveElementTextContent(element, CandyXMLElementEnum.PROTAINS.getValue())));
			if (!CandyValidationHelper.isNutritionalValueValid(value)) {
				throw new ValidationException(CandyValidationHelper.NON_VALID_NUTRITION_VALUE_MESSAGE);
			}
			candy.setNutritionalValue(value);
		} catch (NumberFormatException | DateTimeParseException | ValidationException e) {
			throw new ParserException(e.getMessage(), e);
		}

		candy.setFilling(retrieveElementTextContent(element, CandyXMLElementEnum.FILLING.getValue()));

		String pack = retrieveElementTextContent(element, CandyXMLElementEnum.PACK.getValue());
		if (pack != null) {
			((PackedCandy) candy).setPack(pack);
		}

	}

	private String retrieveElementTextContent(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		Node node = nodeList.item(0);
		return node == null ? null : node.getTextContent();
	}

	private Map<String, Integer> buildIngredients(Element element) throws ParserException {
		String tagName = CandyXMLElementEnum.INGREDIENT.getValue();
		Map<String, Integer> ingredients = new HashMap<>();
		NodeList nodeList = element.getElementsByTagName(tagName);
		try {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				int amount = Integer.parseInt(((Element) node).getAttribute(CandyXMLElementEnum.AMOUNT.getValue()));
				if (amount < 0) {
					throw new ValidationException(CandyValidationHelper.NON_VALID_INGREDIENT_AMOUNT_MESSAGE);
				}
				ingredients.put(node.getTextContent(),
						Integer.parseInt(((Element) node).getAttribute(CandyXMLElementEnum.AMOUNT.getValue())));
			}
		} catch (NumberFormatException | ValidationException e) {
			throw new ParserException(e.getMessage(), e);
		}
		return ingredients;
	}
}
