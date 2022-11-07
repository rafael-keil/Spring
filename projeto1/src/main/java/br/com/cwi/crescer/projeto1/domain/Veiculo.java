package br.com.cwi.crescer.projeto1.domain;

import br.com.cwi.crescer.projeto1.domain.exception.MotoristaComCNHVencidaException;
import br.com.cwi.crescer.projeto1.domain.exception.MotoristaNaoHabilitadoException;
import br.com.cwi.crescer.projeto1.domain.exception.MotoristaSemIdadeMinimaException;

import java.time.LocalDate;

public class Veiculo {

    private Placa placa;
    private Marca marca;
    private String modelo;
    private Integer ano;
    private Cor cor;
    private String foto;
    private CategoriaCarteiraHabilitacao categoria;
    private Integer quantidadeLugares;
    private Motorista proprietario;

    public Veiculo(Placa placa, Marca marca, String modelo, int ano, Cor cor, String foto, CategoriaCarteiraHabilitacao categoria, int quantidadeLugares, Motorista proprietario) throws MotoristaSemIdadeMinimaException, MotoristaComCNHVencidaException, MotoristaNaoHabilitadoException {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.foto = foto;
        this.categoria = categoria;
        this.quantidadeLugares = quantidadeLugares;
        this.proprietario = proprietario;

        if (proprietario == null) {
            throw new MotoristaSemIdadeMinimaException();
        }
        if (proprietario.getCarteiraHabilitacao().getDataVencimento().isBefore(LocalDate.now())) {
            throw new MotoristaComCNHVencidaException();
        }
        if (proprietario.getCarteiraHabilitacao().getCategoria() != categoria) {
            throw new MotoristaNaoHabilitadoException();
        }
    }


}
