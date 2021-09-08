package by.epamtc.sinitsyna.controller.command;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import by.epamtc.sinitsyna.controller.RequestParameterName;
import by.epamtc.sinitsyna.validation.ValidationException;
import by.epamtc.sinitsyna.validation.XSDValidator;

public class ValidationCommand implements Command {
	private static final String VALID_FILE_MESSAGE = "Файл соответствует Candies.xsd.";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart(RequestParameterName.REQUEST_PARAM_FILE_NAME);
		if (filePart.getSize() == 0) {
			request.setAttribute("message", REQUEST_FILE_MESSAGE);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else {
			InputStream fileContent = filePart.getInputStream();
			try {
				boolean isValid = XSDValidator.getInstance().validateXMLSchema(fileContent);
				if (isValid) {
					request.setAttribute("message", VALID_FILE_MESSAGE);
				} else {
					request.setAttribute("message", REQUEST_VALID_FILE_MESSAGE);
				}
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
			//response.sendRedirect(request.getContextPath() + "/Controller");
		}

	}

}
