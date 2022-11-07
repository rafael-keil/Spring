package br.com.cwi.crescer.melevaai.representation.response;

import lombok.Data;

@Data
public class CriarCorridaResponse {
    private String id;
    private VeiculoCriarCorridaResponse veiculoSelecionado;
    private Integer tempoEstimado;
}
