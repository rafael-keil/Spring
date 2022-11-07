package br.com.cwi.crescer.melevaai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Motorista extends Pessoa {

    public static final int IDADE_MINIMA = 18;

    private CarteiraHabilitacao carteiraHabilitacao;
    private boolean isBusy;


    public Motorista(String nome, CPF cpf, LocalDate dataNascimento, String email, ContaVirtual saldo, List<Integer> notas, CarteiraHabilitacao carteiraHabilitacao) {
        super(nome, cpf, dataNascimento, email, saldo, notas);
        this.carteiraHabilitacao = carteiraHabilitacao;
        this.isBusy = false;
    }

    @Override
    protected int getIdadeMinima() {
        return IDADE_MINIMA;
    }

}
