package br.com.cwi.crescer.projeto1.domain;

import br.com.cwi.crescer.projeto1.domain.exception.PassageiroSemIdadeMinimaException;

import java.time.LocalDate;

public class Passageiro extends Pessoa {

    private static final int IDADE_MINIMA = 16;

    public Passageiro(String nome, String cpf, LocalDate dataNascimento, String email) throws PassageiroSemIdadeMinimaException {
        super(nome, cpf, dataNascimento, email);
        if (getIdade() < IDADE_MINIMA) {
            throw new PassageiroSemIdadeMinimaException();
        }
    }
}
