package br.com.cwi.crescer.melevaai.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class JaAvaliadoException extends Exception {
    public JaAvaliadoException(String message) {
        super(message);
    }
}
