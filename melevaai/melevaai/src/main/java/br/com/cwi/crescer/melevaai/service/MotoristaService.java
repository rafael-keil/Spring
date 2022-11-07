package br.com.cwi.crescer.melevaai.service;


import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.mapper.MotoristaMapper;
import br.com.cwi.crescer.melevaai.repository.MotoristaRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosMotoristasResponse;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.melevaai.mapper.MotoristaMapper.toResponse;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository repository;

    @Autowired
    private VeiculoService veiculoService;

    private ModelMapper modelMapper = new ModelMapper();

    public Motorista criarMotorista(CriarMotoristaRequest request) throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        Motorista motorista = MotoristaMapper.toDomain(request);

        if (repository.consultarMotorista(motorista.getCpf().getNumero()) != null) {
            throw new JaCadastradoException("Motorista já cadastrado");
        }
        if(motorista.getCarteiraHabilitacao().isVencida()) {
            throw new MotoristaComCNHVencidaException("CNH Vencida");
        }
        if (motorista.validaIdadeMinima()) {
            throw new ValidacaoNegocioException("Motorista sem idade mínima.");
        }
        if (!motorista.getCpf().isCPF()) {
            throw new ValidacaoNegocioException("CPF inválido.");
        }

        return repository.criarMotorista(motorista);
    }

    public void excluirMotorista(String cpf) throws NaoCadastradoException, MotoristaComVeiculoException {
        Motorista motorista = consultarMotoristaPorCpf(cpf);

        if (veiculoService.isMotoristaComVeiculo(cpf)) {
            throw new MotoristaComVeiculoException("Motorista vinculado à um veículo.");
        }

        repository.removerMotorista(motorista);
    }

    public List<ListarTodosMotoristasResponse> listarMotoristas() {
        return repository.listarMotoristas()
                .stream()
                .map(motorista -> modelMapper.map(motorista, ListarTodosMotoristasResponse.class))
                .collect(Collectors.toList());
    }

    public void sacarCredito(double valor, String cpf) throws SemSaldoException, NaoCadastradoException {

        Motorista motorista = consultarMotoristaPorCpf(cpf);

        if (motorista.getSaldo().getSaldo() < valor) {
            throw new SemSaldoException("Sem saldo suficiente.");
        }

        motorista.getSaldo().saque(valor);
    }

    public MotoristaResponse consultarMotoristaResponsePorCpf(String cpf) throws NaoCadastradoException {

        final Motorista motorista = consultarMotoristaPorCpf(cpf);
        return toResponse(motorista);
    }

    public Motorista consultarMotoristaPorCpf(String cpf) throws NaoCadastradoException {

        Motorista motorista = repository.consultarMotorista(cpf);

        if (motorista == null) {
            throw new NaoCadastradoException("Motorista não cadastrado");
        }

        return motorista;
    }

}
