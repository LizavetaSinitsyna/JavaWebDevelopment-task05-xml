package by.epamtc.sinitsyna.parser;

public enum CandyXMLElementEnum {
	CANDIES("candies"), CANDY("candy"), PACKED_CANDY("packed_candy"), ID("id"), PRODUCER("producer"), GLAZE("glaze"),
	NAME("name"), ENERGY("energy"), PRODUCTION_DATE_TIME("production_date_time"), FILLING("filling"), PACK("pack"),
	FATS("fats"), PROTAINS("protains"), CARBOHYDRATES("carbohydrates"), INGREDIENT("ingredient"), AMOUNT("amount"),
	INGREDIENTS("ingredients"), VALUE("value");

	private String value;

	private CandyXMLElementEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
