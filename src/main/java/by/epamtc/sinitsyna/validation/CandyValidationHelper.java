package by.epamtc.sinitsyna.validation;

import by.epamtc.sinitsyna.bean.NutritionalValue;

public class CandyValidationHelper {
	public static final String NON_VALID_NUTRITION_VALUE_MESSAGE = "Amount of fats, protains and carbohydrates can't be less than 0 and their sum can't be greater than 100.";
	public static final String NON_VALID_ENERGY_MESSAGE = "Energy value can't be less than 0.";
	public static final String NON_VALID_INGREDIENT_AMOUNT_MESSAGE = "Energy value can't be less than 0.";
	private static final int MAX_NUTRITION_SUM = 100;
	private static final String NULL_VALUE_EXCEPTION_MESSAGE = "Passed nutritional value parameter can't be null.";

	public static boolean isNutritionalValueValid(NutritionalValue value) throws ValidationException {
		if (value == null) {
			throw new ValidationException(NULL_VALUE_EXCEPTION_MESSAGE);
		}
		double carbohydrates = value.getCarbohydrates();
		double fats = value.getFats();
		double protains = value.getProtains();
		if (carbohydrates < 0 || fats < 0 || protains < 0) {
			return false;
		}

		return value.getCarbohydrates() + value.getFats() + value.getProtains() <= MAX_NUTRITION_SUM;
	}

}
