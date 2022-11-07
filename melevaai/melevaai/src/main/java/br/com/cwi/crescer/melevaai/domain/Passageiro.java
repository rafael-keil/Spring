package br.com.cwi.crescer.melevaai.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Passageiro extends Pessoa {

    public static final int IDADE_MINIMA = 16;

    public Passageiro(String nome, CPF cpf, LocalDate dataNascimento, String email, ContaVirtual saldo, List<Integer> notas) {
        super(nome, cpf, dataNascimento, email, saldo, notas);
    }

    @Override
    protected int getIdadeMinima() {
        return IDADE_MINIMA;
    }

}
