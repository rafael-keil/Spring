package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.Coordenada;
import br.com.cwi.crescer.melevaai.domain.Corrida;
import br.com.cwi.crescer.melevaai.representation.request.CriarCorridaRequest;
import br.com.cwi.crescer.melevaai.representation.response.CriarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.IniciarCorridaResponse;
import org.modelmapper.ModelMapper;

public class CorridaMapper {
    public static Corrida toDomain(CriarCorridaRequest request) {
        Coordenada coordenadaInicio = new Coordenada(request.getxInicio(), request.getyInicio());
        Coordenada coordenadaFim = new Coordenada(request.getxFim(), request.getyFim());

        return new Corrida(coordenadaInicio, coordenadaFim,
                request.getVeiculo(), request.getPassageiro());

    }

    public static IniciarCorridaResponse toIniciarCorridaResponse(double tempoEstimado, double valor) {

        return new IniciarCorridaResponse( tempoEstimado, valor);

    }

    public static CriarCorridaResponse toCriarCorridaResponse(Corrida corrida) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(corrida, CriarCorridaResponse.class);


    }
}
