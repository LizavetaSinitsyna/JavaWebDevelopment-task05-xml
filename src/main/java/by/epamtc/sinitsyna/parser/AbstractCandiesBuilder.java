package by.epamtc.sinitsyna.parser;

import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;

import by.epamtc.sinitsyna.bean.Candy;

public abstract class AbstractCandiesBuilder {
	protected static final String NULL_FILE_EXCEPTION = "Файл не должен быть равен null.";

	protected Set<Candy> candies;
	
	public AbstractCandiesBuilder() {
		candies = new TreeSet<>();
	}
	
	public Set<Candy> getCandies() {
		return candies;
	}
	
	public abstract void buildCandySet(InputStream fileContent) throws ParserException;
	

}
