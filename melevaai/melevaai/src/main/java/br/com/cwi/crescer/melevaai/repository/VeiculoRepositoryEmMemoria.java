package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.CategoriaCarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VeiculoRepositoryEmMemoria implements VeiculoRepository {

    private static final List<Veiculo> veiculos = new ArrayList<>();

    @Override
    public void cadastrarVeiculo(final Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    @Override
    public List<Veiculo> listarVeiculos() {
        return veiculos;
    }

    @Override
    public List<Veiculo> consultarVeiculoPorCategoria( CategoriaCarteiraHabilitacao categoria) {
        return veiculos.stream()
            .filter(veiculo -> veiculo.getCategoriaCarteiraHabilitacao() == categoria)
            .collect(Collectors.toList());
    }
}
