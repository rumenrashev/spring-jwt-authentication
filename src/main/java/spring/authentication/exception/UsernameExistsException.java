package spring.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static spring.authentication.exception.ExceptionMessages.USERNAME_EXISTS_MESSAGE;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException() {
        super(USERNAME_EXISTS_MESSAGE);
    }

}
