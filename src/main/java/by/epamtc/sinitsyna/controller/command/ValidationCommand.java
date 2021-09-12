package by.epamtc.sinitsyna.controller.command;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.sinitsyna.controller.RequestParameterName;
import by.epamtc.sinitsyna.validation.ValidationException;
import by.epamtc.sinitsyna.validation.XSDValidator;

public class ValidationCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ValidationCommand.class.getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart(RequestParameterName.REQUEST_PARAM_FILE_NAME);
		RedirectHelper redirectHelper = RedirectHelper.getInstance();
		if (filePart.getSize() == 0) {
			redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
					RedirectHelper.REQUEST_FILE_MESSAGE, "/index.jsp");
		} else {
			InputStream fileContent = filePart.getInputStream();
			try {
				boolean isValid = XSDValidator.getInstance().validateXMLSchema(fileContent);
				if (isValid) {
					redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
							RedirectHelper.VALID_FILE_MESSAGE, "/index.jsp");
				} else {
					redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
							RedirectHelper.INVALID_FILE_MESSAGE, "/index.jsp");
				}
			} catch (ValidationException e) {
				LOG.error(e);
				redirectHelper.redirectWithSessionAttribute(request, response, AttributeName.MESSAGE,
						RedirectHelper.VALIDATION_EXCEPTION_MESSAGE, "/index.jsp");
			}
		}

	}

}
