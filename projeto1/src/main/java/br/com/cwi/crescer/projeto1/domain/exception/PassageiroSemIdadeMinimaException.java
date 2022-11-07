package br.com.cwi.crescer.projeto1.domain.exception;

public class PassageiroSemIdadeMinimaException extends Exception {

    public PassageiroSemIdadeMinimaException() {
        super("Passageiro sem idade");
    }
}
