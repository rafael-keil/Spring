package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.mapper.VeiculoMapper;
import br.com.cwi.crescer.melevaai.repository.VeiculoRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosVeiculosResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private MotoristaService motoristaService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<ListarTodosVeiculosResponse> listarVeiculos() {
        return repository.findAll()
                .stream()
                .map(motorista -> modelMapper.map(motorista, ListarTodosVeiculosResponse.class))
                .collect(Collectors.toList());
    }

    public Veiculo criarVeiculo(CriarVeiculoRequest request) throws NaoCadastradoException, MotoristaNaoHabilitadoException, JaCadastradoException {

        Motorista motorista = motoristaService.consultarMotoristaPorCpf(new CPF(request.getCpfMotorista()));

        request.setMotorista(motorista);

        Veiculo veiculo = VeiculoMapper.toDomain(request);

        if (repository.findAll().contains(veiculo)){
            throw new JaCadastradoException("Veiculo Já Cadastrado.");
        }
        if (!veiculo.isCategoriaCnhProprietarioValid()) {
            throw new MotoristaNaoHabilitadoException("Motorista não habilitado.");
        }

        return repository.save(veiculo);
    }

    public Veiculo getVeiculoAleatorio() throws ValidacaoNegocioException {
        List<Veiculo> veiculosFiltrados = repository
                .findAll()
                .stream()
                .filter(veiculo -> !veiculo.getMotorista().isBusy() && !veiculo.getMotorista().getCarteiraHabilitacao().isVencida())
                .collect(Collectors.toList());

        if (veiculosFiltrados.isEmpty()) {
            throw new ValidacaoNegocioException("Nenhum veiculo disponível");
        }
        if (veiculosFiltrados.size() == 1) {
            return veiculosFiltrados.get(0);
        }

        int indexAleatorio = ThreadLocalRandom.current().nextInt(0, veiculosFiltrados.size() - 1);
        return veiculosFiltrados.get(indexAleatorio);
    }

    public boolean isMotoristaComVeiculo(String cpf){

        return repository.findAll()
                .stream()
                .anyMatch(veiculo -> veiculo.getMotorista().getCpf().getNumero().equals(cpf));
    }
}
