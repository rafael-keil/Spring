package br.com.cwi.crescer.melevaai.representation.response;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.CarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.ContaVirtual;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MotoristaResponse {

    private CarteiraHabilitacao carteiraHabilitacao;
    private boolean isBusy;
    private String nome;
    private CPF cpf;
    private LocalDate dataNascimento;
    private String email;
    private ContaVirtual saldo;
    private List<Integer> notas;

}
