package br.com.cwi.crescer.melevaai.representation.response;

import br.com.cwi.crescer.melevaai.domain.CPF;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ListarTodosMotoristasResponse {

    private String nome;

    private CPF cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

}
