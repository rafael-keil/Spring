package br.com.cwi.crescer.melevaai.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "mla_passageiro")
public class Passageiro extends Pessoa {

    public static final int IDADE_MINIMA = 16;

    public Passageiro(String nome, CPF cpf, LocalDate dataNascimento, String email) {
        super(nome, cpf, dataNascimento, email);
    }

    @Override
    protected int getIdadeMinima() {
        return IDADE_MINIMA;
    }

}
