package br.com.cwi.crescer.melevaai.fixture;

import br.com.cwi.crescer.melevaai.domain.CategoriaCarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.Cor;
import br.com.cwi.crescer.melevaai.domain.Marca;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;

public class VeiculoFixture {

    public static Veiculo veiculoCompleto(){

        return new Veiculo(
                "123aassd",
                Marca.AUDI,
                "A2",
                2001,
                Cor.BRANCO,
                "google.com",
                CategoriaCarteiraHabilitacao.A,
                4,
                MotoristaFixture.motoristaCompleto()
        );
    }

    public static CriarVeiculoRequest requestCompleto() {

        return new CriarVeiculoRequest(
                "123aassd",
                "AUDI",
                "A2",
                2001,
                "BRANCO",
                "google.com",
                4,
                "A",
                "82089223073",
                MotoristaFixture.motoristaCompleto()
        );
    }


}
