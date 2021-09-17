package by.epamtc.sinitsyna.controller.command;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.sinitsyna.bean.Candy;
import by.epamtc.sinitsyna.controller.RequestParameterName;
import by.epamtc.sinitsyna.parser.AbstractCandiesBuilder;
import by.epamtc.sinitsyna.parser.CandiesBuilderFactory;
import by.epamtc.sinitsyna.parser.ParserException;

public class ParsingCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ParsingCommand.class.getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart(RequestParameterName.REQUEST_PARAM_FILE_NAME);
		RedirectHelper redirectHelper = RedirectHelper.getInstance();
		if (filePart.getSize() == 0) {
			redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
					RedirectHelper.REQUEST_FILE_MESSAGE, "/index.jsp");
		} else {
			try (InputStream fileContent = filePart.getInputStream()) {
				String parserType = request.getParameter(RequestParameterName.REQUEST_PARAM_PARSER_NAME);
				if (parserType == null) {
					redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
							RedirectHelper.REQUEST_PARSER_TYPE_MESSAGE, "/index.jsp");
				} else {
					CandiesBuilderFactory candiesBuilderFactory = CandiesBuilderFactory.getInstance();
					try {
						AbstractCandiesBuilder candiesBuilder = candiesBuilderFactory.createCandiesBuilder(parserType);
						candiesBuilder.buildCandySet(fileContent);
						Set<Candy> candies = candiesBuilder.getCandies();
						// According to the task we can print result in console instead of printing on
						// the page.
						printCandies(candies);
						redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.CANDIES, candies,
								"/index.jsp");
					} catch (ParserException e) {
						LOG.error(e);
						redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
								RedirectHelper.REQUEST_VALID_FILE_MESSAGE, "/index.jsp");
					}
				}
			}
		}

	}

	public void printCandies(Set<Candy> candies) {
		for (Candy el : candies) {
			System.out.println(el);
		}
	}

}
