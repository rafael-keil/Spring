package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.*;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;

public class VeiculoMapper {

    public static Veiculo toDomain(CriarVeiculoRequest request){

        return new Veiculo(request.getPlaca(),
                Marca.valueOf(request.getMarca()),
                request.getModelo(),
                request.getAno(),
                Cor.valueOf(request.getCor()),
                request.getUrlFoto(),
                CategoriaCarteiraHabilitacao.valueOf(request.getCategoriaCnh()),
                request.getLugares() != null ? request.getLugares() : 0,
                request.getMotorista());
    }

}
