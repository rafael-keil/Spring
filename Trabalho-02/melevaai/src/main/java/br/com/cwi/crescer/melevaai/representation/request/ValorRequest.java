package br.com.cwi.crescer.melevaai.representation.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValorRequest {

    @NotNull
    private double valor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
