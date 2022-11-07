package br.com.cwi.crescer.melevaai.representation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarPassageiroRequest {

    @NotEmpty
    private String nome;

    @NotEmpty
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Email(message = "O e-mail deve ser em um formato válido.")
    private String email;

}
