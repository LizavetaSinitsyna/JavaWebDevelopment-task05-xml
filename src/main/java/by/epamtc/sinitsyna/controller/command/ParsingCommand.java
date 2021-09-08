package by.epamtc.sinitsyna.controller.command;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import by.epamtc.sinitsyna.bean.Candy;
import by.epamtc.sinitsyna.controller.RequestParameterName;
import by.epamtc.sinitsyna.parser.AbstractCandiesBuilder;
import by.epamtc.sinitsyna.parser.CandiesBuilderFactory;
import by.epamtc.sinitsyna.parser.ParserException;

public class ParsingCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart(RequestParameterName.REQUEST_PARAM_FILE_NAME);
		if (filePart.getSize() == 0) {
			forwardWithMessage(request, response, REQUEST_FILE_MESSAGE, "/index.jsp");
		} else {
			InputStream fileContent = filePart.getInputStream();
			String parserType = request.getParameter(RequestParameterName.REQUEST_PARAM_PARSER_NAME);
			CandiesBuilderFactory candiesBuilderFactory = CandiesBuilderFactory.getInstance();
			try {
				AbstractCandiesBuilder candiesBuilder = candiesBuilderFactory.createCandiesBuilder(parserType);

				candiesBuilder.buildCandySet(fileContent);
				Set<Candy> candies = candiesBuilder.getCandies();
				printCandies(candies);
				request.setAttribute("candies", candies);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				// response.sendRedirect(request.getContextPath() + "/Controller");
			} catch (ParserException e) {
				forwardWithMessage(request, response, REQUEST_VALID_FILE_MESSAGE, "/index.jsp");
			}
		}

	}

	private void forwardWithMessage(HttpServletRequest request, HttpServletResponse response, String message,
			String jsp) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher(jsp).forward(request, response);
	}

	public void printCandies(Set<Candy> candies) {
		for (Candy el : candies) {
			System.out.println(el);
		}
	}

}
