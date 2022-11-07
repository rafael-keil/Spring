package br.com.cwi.crescer.melevaai.representation.response;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.ContaVirtual;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PassageiroResponse {

    private String nome;
    private CPF cpf;
    private LocalDate dataNascimento;
    private String email;
    private ContaVirtual saldo;
    private List<Integer> notas;

}
