package br.com.cwi.crescer.melevaai.representation.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CriarMotoristaRequest {

    @NotEmpty
    private String nome;

    @NotEmpty
    private String cpf;

    @NotEmpty
    @Email(message = "O e-mail deve ser em um formato válido.")
    private String email;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Valid
    private CriarCarteiraHabilitacaoRequest carteiraHabilitacao;

}
