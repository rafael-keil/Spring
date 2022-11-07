package br.com.cwi.crescer.melevaai.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CorridaIndisponivelException extends Exception {
    public CorridaIndisponivelException(String message) {
        super(message);
    }
}
