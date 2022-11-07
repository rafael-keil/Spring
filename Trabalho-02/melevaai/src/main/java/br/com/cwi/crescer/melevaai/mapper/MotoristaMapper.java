package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.CarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.representation.request.CriarCarteiraHabilitacaoRequest;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import org.modelmapper.ModelMapper;

public class MotoristaMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static Motorista toDomain(CriarMotoristaRequest request) {
        CriarCarteiraHabilitacaoRequest cnhRequest = request.getCarteiraHabilitacao();

        CarteiraHabilitacao cnh = new CarteiraHabilitacao(cnhRequest.getCategoria(), cnhRequest.getNumero(), cnhRequest.getDataVencimento());

        return new Motorista(request.getNome(), new CPF(request.getCpf()),
                request.getDataNascimento(), request.getEmail(), cnh);

    }

    public static MotoristaResponse toResponse(Motorista motorista){
        return modelMapper.map(motorista, MotoristaResponse.class);
    }
}
