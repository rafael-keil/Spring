package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.CarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.ContaVirtual;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.representation.request.CriarCarteiraHabilitacaoRequest;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MotoristaMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static Motorista toDomain(CriarMotoristaRequest request) {
        CriarCarteiraHabilitacaoRequest cnhRequest = request.getCarteiraHabilitacao();
        List<Integer> notas = new ArrayList<>();

        ContaVirtual contaVirtual = new ContaVirtual(0);
        CarteiraHabilitacao cnh = new CarteiraHabilitacao(cnhRequest.getCategoria(), cnhRequest.getNumero(), cnhRequest.getDataVencimento());

        return new Motorista(request.getNome(), new CPF(request.getCpf()),
                request.getDataNascimento(), request.getEmail(), contaVirtual, notas, cnh);

    }

    public static MotoristaResponse toResponse(Motorista motorista){
        return modelMapper.map(motorista, MotoristaResponse.class);
    }
}
