package br.com.cwi.crescer.melevaai.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class SemSaldoException extends Exception {
    public SemSaldoException(String message) {
        super(message);
    }
}
