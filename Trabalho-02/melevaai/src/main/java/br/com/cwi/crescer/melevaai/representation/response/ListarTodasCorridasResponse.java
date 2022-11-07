package br.com.cwi.crescer.melevaai.representation.response;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Coordenada;
import br.com.cwi.crescer.melevaai.domain.EstadoCorrida;
import lombok.Data;

import java.util.UUID;

@Data
public class ListarTodasCorridasResponse {

    public UUID id;
    public CPF cpfPassageiro;
    public VeiculoCriarCorridaResponse veiculoSelecionado;
    public EstadoCorrida estadoCorrida;
    public double tempoEstimado;
    public boolean motoristaAvaliado;
    public boolean passageiroAvaliado;
    private Coordenada coordenadaInicio;
    private Coordenada coordenadaFim;

}
