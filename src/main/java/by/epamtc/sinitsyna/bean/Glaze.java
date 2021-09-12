package by.epamtc.sinitsyna.bean;

public enum Glaze {
	UNGLAZED("без глазури"), CHOCOLATE("шоколадная"), FAT("жировая"), CARAMEL("карамельная"), SUGAR("сахарная"),
	FONDANT("помадная"), PECTIN("пектиновая");

	private String type;

	private Glaze(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;

	}
}