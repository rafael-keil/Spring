package br.com.cwi.crescer.melevaai.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidacaoNegocioException extends Exception {
    public ValidacaoNegocioException(String message) {
        super(message);
    }
}
