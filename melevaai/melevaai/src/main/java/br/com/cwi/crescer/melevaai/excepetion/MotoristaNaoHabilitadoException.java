package br.com.cwi.crescer.melevaai.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MotoristaNaoHabilitadoException extends Exception {
    public MotoristaNaoHabilitadoException(String message) {
        super(message);
    }
}
