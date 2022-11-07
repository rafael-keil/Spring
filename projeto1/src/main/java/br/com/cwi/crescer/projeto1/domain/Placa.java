package br.com.cwi.crescer.projeto1.domain;

public class Placa {
    private UF uf;
    private String cidade;
    private String digito;

    public Placa(UF uf, String cidade, String digito) {
        this.uf = uf;
        this.cidade = cidade;
        this.digito = digito;
    }
}
