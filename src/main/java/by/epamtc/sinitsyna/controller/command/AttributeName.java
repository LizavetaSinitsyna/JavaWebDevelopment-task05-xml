package by.epamtc.sinitsyna.controller.command;

public enum AttributeName {
	MESSAGE("message"), CANDIES("candies");
	
	private String name;
		
	AttributeName(String string) {
		this.name = string;
	}	
	
	public String getValue() {
		return name;
	}

}
