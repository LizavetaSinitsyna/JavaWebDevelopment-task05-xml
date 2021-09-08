package by.epamtc.sinitsyna.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	String REQUEST_FILE_MESSAGE = "Пожалуйста, выберите файл.";
	String REQUEST_VALID_FILE_MESSAGE = "Файл не соответствует .xsd. Пожалуйста, выберите иной файл.";
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
