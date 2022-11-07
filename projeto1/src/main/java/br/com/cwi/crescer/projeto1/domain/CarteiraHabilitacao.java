package br.com.cwi.crescer.projeto1.domain;

import java.time.LocalDate;

public class CarteiraHabilitacao {

    private CategoriaCarteiraHabilitacao categoria;
    private String numero;
    private LocalDate dataVencimento;

    public CarteiraHabilitacao(CategoriaCarteiraHabilitacao categoria, String numero, LocalDate dataVencimento) {
        this.categoria = categoria;
        this.numero = numero;
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public CategoriaCarteiraHabilitacao getCategoria() {
        return categoria;
    }
}
