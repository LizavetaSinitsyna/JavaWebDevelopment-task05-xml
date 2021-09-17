package by.epamtc.sinitsyna.parser.stax;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epamtc.sinitsyna.bean.Candy;
import by.epamtc.sinitsyna.bean.Glaze;
import by.epamtc.sinitsyna.bean.NutritionalValue;
import by.epamtc.sinitsyna.bean.PackedCandy;
import by.epamtc.sinitsyna.parser.ParserException;

public class CandiesStAXBuilderTest {
	private InputStream file;
	private Set<Candy> candies;
	private CandiesStAXBuilder stax;

	@Before
	public void init() throws ParserException {
		stax = new CandiesStAXBuilder();
		candies = new TreeSet<Candy>();

		Candy candy = new Candy();
		candy.setId("ID_1");
		candy.setName("Минский грильяж");
		candy.setProducer("СОАО \"Коммунарка\"");
		candy.setGlaze(Glaze.CHOCOLATE);
		candy.setEnergy(388);
		candy.setProductionDateTime(LocalDateTime.parse("2021-08-29T13:00:00"));
		Map<String, Integer> ingredients = new HashMap<>();
		ingredients.put("caхар", 30);
		ingredients.put("арахис", 20);
		ingredients.put("пюре яблочное", 20);
		candy.setIngredients(ingredients);
		NutritionalValue value = new NutritionalValue();
		value.setCarbohydrates(74.1);
		value.setFats(10.4);
		value.setProtains(2.5);
		candy.setNutritionalValue(value);
		candy.setFilling("Фруктовый, Ореховый");
		candies.add(candy);

		PackedCandy packedCandy = new PackedCandy();
		packedCandy.setId("ID_2");
		packedCandy.setName("Ирис");
		packedCandy.setProducer("ОАО \"Красный Мозырянин\"");
		candy.setGlaze(Glaze.UNGLAZED);
		packedCandy.setEnergy(410);
		packedCandy.setProductionDateTime(LocalDateTime.parse("2021-08-29T13:30:00"));
		ingredients.clear();
		ingredients.put("caхар", 20);
		ingredients.put("сгущенное молоко", 50);
		ingredients.put("сливочное масло", 20);
		packedCandy.setIngredients(ingredients);
		value.setCarbohydrates(82);
		value.setFats(7.5);
		value.setProtains(2.5);
		packedCandy.setNutritionalValue(value);
		packedCandy.setFilling("Ирис");
		packedCandy.setPack("Флоу-пак");
		candies.add(packedCandy);
	}

	@Test
	public void testBuildCandySetByPassingValidFile() throws ParserException, IOException {
		file = getClass().getClassLoader().getResource("./resource/ValidCandies.xml").openStream();
		stax.buildCandySet(file);
		Assert.assertEquals(candies, stax.getCandies());
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingInvalidFileWithNegativeIngredientAmount()
			throws ParserException, IOException {
		file = getClass().getClassLoader().getResource("./resource/InvalidCandiesWithNegativeIngredientAmount.xml")
				.openStream();
		stax.buildCandySet(file);
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingInvalidFileWithInvalidNutritionalValue() throws ParserException, IOException {
		file = getClass().getClassLoader().getResource("./resource/InvalidCandiesWithInvalidNutritionalValue.xml")
				.openStream();
		stax.buildCandySet(file);
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingInvalidFileWithNegativeEnergy() throws ParserException, IOException {
		file = getClass().getClassLoader().getResource("./resource/InvalidCandiesWithNegativeEnergy.xml").openStream();
		stax.buildCandySet(file);
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingInvalidFileWithInvalidOpenTagElementName()
			throws ParserException, IOException {
		file = getClass().getClassLoader()
				.getResource("./resource/InvalidCandiesWithInvalidValueElementOpenTagName.xml").openStream();
		stax.buildCandySet(file);
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingInvalidFileWithInvalidDateElement() throws ParserException, IOException {
		file = getClass().getClassLoader().getResource("./resource/InvalidCandiesWithInvalidDateElement.xml")
				.openStream();
		stax.buildCandySet(file);
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingInvalidFileWithInvalidProtainsValueElement()
			throws ParserException, IOException {
		file = getClass().getClassLoader().getResource("./resource/InvalidCandiesWithInvalidProtainsValue.xml")
				.openStream();
		stax.buildCandySet(file);
	}

	@Test(expected = ParserException.class)
	public void testBuildCandySetByPassingNull() throws ParserException, IOException {
		stax.buildCandySet(null);
	}

	@After
	public void close() throws IOException {
		if (file != null) {
			file.close();
		}
	}

}
