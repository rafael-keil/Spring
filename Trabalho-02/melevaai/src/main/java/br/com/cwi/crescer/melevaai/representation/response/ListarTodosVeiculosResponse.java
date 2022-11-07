package br.com.cwi.crescer.melevaai.representation.response;

import br.com.cwi.crescer.melevaai.domain.Cor;
import br.com.cwi.crescer.melevaai.domain.Marca;
import lombok.Data;

@Data
public class ListarTodosVeiculosResponse {

    private String placa;
    private Marca marca;
    private String modelo;
    private int ano;
    private Cor cor;
    private ListarTodosMotoristasResponse motorista;

}
