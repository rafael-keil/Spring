package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;
import br.com.cwi.crescer.melevaai.representation.response.PassageiroResponse;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class PassageiroMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static Passageiro toDomain(CriarPassageiroRequest request) {
        List<Integer> notas = new ArrayList<>();

        return new Passageiro(request.getNome(), new CPF(request.getCpf()), request.getDataNascimento(), request.getEmail());

    }

    public static PassageiroResponse toResponse(Passageiro passageiro){
        return modelMapper.map(passageiro, PassageiroResponse.class);
    }
}
