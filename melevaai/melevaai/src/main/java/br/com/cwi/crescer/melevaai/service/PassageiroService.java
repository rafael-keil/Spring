package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.excepetion.JaCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.NaoCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.SemSaldoException;
import br.com.cwi.crescer.melevaai.excepetion.ValidacaoNegocioException;
import br.com.cwi.crescer.melevaai.mapper.PassageiroMapper;
import br.com.cwi.crescer.melevaai.repository.PassageiroRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosPassageirosResponse;
import br.com.cwi.crescer.melevaai.representation.response.PassageiroResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.melevaai.mapper.PassageiroMapper.toResponse;


@Service
public class PassageiroService {

    @Autowired
    private PassageiroRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    public void criarPassageiro(CriarPassageiroRequest request) throws JaCadastradoException, ValidacaoNegocioException {

        Passageiro passageiro = PassageiroMapper.toDomain(request);

        if (repository.consultarPassageiro(passageiro.getCpf().getNumero()) != null) {
            throw new JaCadastradoException("Passageiro já cadastrado");
        }
        if (passageiro.validaIdadeMinima()) {
            throw new ValidacaoNegocioException("Passageiro sem idade mínima.");
        }
        if (!passageiro.getCpf().isCPF()) {
            throw new ValidacaoNegocioException("CPF inválido.");
        }

        repository.criarPassageiro(passageiro);

    }

    public void depositarCredito(double valor, String cpf) throws SemSaldoException, NaoCadastradoException {

        Passageiro passageiro = consultarPassageiroPorCpf(cpf);

        if (valor < 0) {
            throw new SemSaldoException("Valor inválido.");
        }

        passageiro.getSaldo().deposito(valor);
    }

    public List<ListarTodosPassageirosResponse> listarPassageiros() {
        return repository.listarPassageiros().stream()
                .map(passageiro -> modelMapper.map(passageiro, ListarTodosPassageirosResponse.class))
                .collect(Collectors.toList());
    }

    public PassageiroResponse consultarPassageiroResponsePorCpf(final String cpf) throws NaoCadastradoException {

        final Passageiro passageiro = consultarPassageiroPorCpf(cpf);

        return toResponse(passageiro);
    }

    public Passageiro consultarPassageiroPorCpf(final String cpf) throws NaoCadastradoException {

        final Passageiro passageiro = repository.consultarPassageiro(cpf);

        if (passageiro == null) {
            throw new NaoCadastradoException("Motorista não cadastrado");
        }

        return passageiro;
    }

}
