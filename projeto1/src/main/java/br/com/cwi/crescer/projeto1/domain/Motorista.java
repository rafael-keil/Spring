package br.com.cwi.crescer.projeto1.domain;

import br.com.cwi.crescer.projeto1.domain.exception.MotoristaSemIdadeMinimaException;

import java.time.LocalDate;

public class Motorista extends Pessoa {

    private static final int IDADE_MINIMA = 18;

    private CarteiraHabilitacao carteiraHabilitacao;

    public Motorista(String nome, String cpf, LocalDate dataNascimento, String email, CarteiraHabilitacao carteiraHabilitacao) throws MotoristaSemIdadeMinimaException {
        super(nome, cpf, dataNascimento, email);
        if (getIdade() < IDADE_MINIMA) {
            throw new MotoristaSemIdadeMinimaException();
        }
        this.carteiraHabilitacao = carteiraHabilitacao;
    }

    public CarteiraHabilitacao getCarteiraHabilitacao() {
        return carteiraHabilitacao;
    }
}
