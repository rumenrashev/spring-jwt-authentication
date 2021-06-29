package spring.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static spring.authentication.exception.ExceptionMessages.EMAIL_EXISTS_MESSAGE;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = EMAIL_EXISTS_MESSAGE)
public class EmailExistsException extends RuntimeException {

}
