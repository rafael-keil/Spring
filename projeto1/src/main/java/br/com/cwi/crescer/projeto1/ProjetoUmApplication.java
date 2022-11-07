package br.com.cwi.crescer.projeto1;

import br.com.cwi.crescer.projeto1.domain.*;
import br.com.cwi.crescer.projeto1.domain.exception.*;

import java.time.LocalDate;

public class ProjetoUmApplication {
    public static void main(String[] args) {

        try {
            Placa placa1 = new Placa(UF.RS, "Canoas", "80780513iop");
            CarteiraHabilitacao habilitacao1 = new CarteiraHabilitacao(CategoriaCarteiraHabilitacao.B, "12654892", LocalDate.of(2030, 8, 15));
            Motorista motorista1 = new Motorista("Diuliano", "08358327", LocalDate.of(2003, 8, 15), "email@email.com", habilitacao1);

            Veiculo veiculo = new Veiculo(placa1, Marca.AUDI, "Q3", 2015, Cor.Branco,
                    "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.luxurymotorsport.com.br%2Fwp-content%2Fuploads%2F2020%2F04%2FAudi-Q3-2013-2.0-Quattro-Branca-01.jpg&imgrefurl=https%3A%2F%2Fwww.luxurymotorsport.com.br%2Fcarros%2Faudi-q3-attraction-2-0-quattro-branca%2F&tbnid=l8HluOwc0mNNBM&vet=12ahUKEwjYysH73aLzAhXlvZUCHTOIA0YQMygMegUIARDLAQ..i&docid=JhURfOMLlVJAMM&w=1265&h=792&q=audi%20q3%20branco&client=opera-gx&ved=2ahUKEwjYysH73aLzAhXlvZUCHTOIA0YQMygMegUIARDLAQ",
                    CategoriaCarteiraHabilitacao.B, 4, motorista1);

            System.out.println(veiculo);
        } catch (MotoristaSemIdadeMinimaException | MotoristaComCNHVencidaException | MotoristaNaoHabilitadoException exception) {
            System.out.println(exception.getMessage());
        }


    }
}
