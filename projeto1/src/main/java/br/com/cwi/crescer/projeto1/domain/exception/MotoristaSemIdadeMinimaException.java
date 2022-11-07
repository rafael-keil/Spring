package br.com.cwi.crescer.projeto1.domain.exception;

public class MotoristaSemIdadeMinimaException extends Exception {

    public MotoristaSemIdadeMinimaException() {
        super("Motorista sem idade");
    }
}
