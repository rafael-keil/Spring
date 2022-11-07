package br.com.cwi.crescer.melevaai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "mla_pessoa")
@SequenceGenerator(name = "mla_seq_pessoa", sequenceName = "mla_seq_pessoa", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mla_seq_pessoa")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "cpf"))
    private CPF cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "email")
    private String email;

    @Embedded
    @AttributeOverride(name = "saldo", column = @Column(name = "saldo"))
    private ContaVirtual saldo;

    @ElementCollection
    @CollectionTable(name = "mla_notas")
    private List<Integer> notas = new ArrayList<>();

    protected Pessoa(String nome, CPF cpf, LocalDate dataNascimento, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;

        this.saldo = new ContaVirtual(0);
    }

    @JsonIgnore
    public int getIdade() {
        return Period.between(this.getDataNascimento(), LocalDate.now()).getYears();
    }

    protected abstract int getIdadeMinima();

    public boolean validaIdadeMinima() {
        int idadeMinima = this.getIdadeMinima();
        return this.getIdade() < idadeMinima;
    }

    public double mediaNotas() {
        double somaNota = notas.stream().reduce(0, Integer::sum);
        return somaNota / notas.size();
    }

}
