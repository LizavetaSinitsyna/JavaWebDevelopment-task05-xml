package by.epamtc.sinitsyna.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectHelper {
	public static final String VALID_FILE_MESSAGE = "Файл соответствует Candies.xsd.";
	public static final String INVALID_FILE_MESSAGE = "Файл не соответствует Candies.xsd.";
	public static final String REQUEST_FILE_MESSAGE = "Пожалуйста, выберите файл.";
	public static final String REQUEST_VALID_FILE_MESSAGE = "Во время парсинга возникла ошибка. Возможно файл не соответствует Candies.xsd.";
	public static final String REQUEST_PARSER_TYPE_MESSAGE = "Пожалуйста, выберите тип парсера.";
	public static final String VALIDATION_EXCEPTION_MESSAGE = "Во время валидации возникла ошибка, пожалуйста, попробуйте позже.";

	private RedirectHelper() {

	}

	private static class SingletonHelper {
		private static final RedirectHelper INSTANCE = new RedirectHelper();
	}

	public static RedirectHelper getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public void redirectWithSessionAttribute(HttpServletRequest request, HttpServletResponse response,
			AttributeName attrName, Object attr, String jsp) throws ServletException, IOException {
		HttpSession session = request.getSession();
		AttributeName[] attributes = AttributeName.values();
		for (AttributeName element : attributes) {
			session.removeAttribute(element.getValue());
		}
		session.setAttribute(attrName.getValue(), attr);
		response.sendRedirect(request.getContextPath() + jsp);
	}

}
