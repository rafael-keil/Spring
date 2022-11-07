package br.com.cwi.crescer.melevaai.representation.response;

import lombok.Data;

@Data
public class VeiculoCriarCorridaResponse {
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String urlFoto;
    private MotoristaCriarCorridaResponse motorista;
}
