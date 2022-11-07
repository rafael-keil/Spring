package br.com.cwi.crescer.projeto1.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Pessoa {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }

    public long getIdade() {
        return dataNascimento.until(LocalDate.now(), ChronoUnit.YEARS);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }
}
