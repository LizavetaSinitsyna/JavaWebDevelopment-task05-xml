package by.epamtc.sinitsyna.parser;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.sinitsyna.bean.Glaze;

public class GlazeProvider {
	private Map<String, Glaze> glazeTypes;
	{
		glazeTypes = new HashMap<>();
		Glaze[] allGlazeTypes = Glaze.values();
		for (int i = 0; i < allGlazeTypes.length; i++) {
			glazeTypes.put(allGlazeTypes[i].getType(), allGlazeTypes[i]);
		}
	}

	private GlazeProvider() {

	}

	private static class SingletonHelper {
		private static final GlazeProvider INSTANCE = new GlazeProvider();
	}

	public static GlazeProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public Glaze getGlazeType(String type) {
		return glazeTypes.get(type);
	}

}
