package be.pxl.paj.testing.api;

import be.pxl.paj.testing.exception.InvalidColorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidColorException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	String invalidColorExceptionHandler(InvalidColorException ex) {
		return ex.getMessage();
	}
}
