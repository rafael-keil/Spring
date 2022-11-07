package br.com.cwi.crescer.melevaai.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "mla_motorista")
public class Motorista extends Pessoa {

    public static final int IDADE_MINIMA = 18;

    @OneToOne(cascade = CascadeType.ALL )
    private CarteiraHabilitacao carteiraHabilitacao;

    @Column(name = "is_busy")
    private boolean isBusy;


    public Motorista(String nome, CPF cpf, LocalDate dataNascimento, String email, CarteiraHabilitacao carteiraHabilitacao) {
        super(nome, cpf, dataNascimento, email);
        this.carteiraHabilitacao = carteiraHabilitacao;
        this.isBusy = false;
    }

    @Override
    protected int getIdadeMinima() {
        return IDADE_MINIMA;
    }

}
