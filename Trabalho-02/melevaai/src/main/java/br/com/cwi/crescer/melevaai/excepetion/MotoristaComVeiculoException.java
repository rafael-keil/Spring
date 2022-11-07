package br.com.cwi.crescer.melevaai.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MotoristaComVeiculoException extends Exception {
    public MotoristaComVeiculoException(String message) {
        super(message);
    }
}
