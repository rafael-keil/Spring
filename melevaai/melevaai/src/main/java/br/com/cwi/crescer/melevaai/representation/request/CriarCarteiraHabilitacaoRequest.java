package br.com.cwi.crescer.melevaai.representation.request;

import br.com.cwi.crescer.melevaai.domain.CategoriaCarteiraHabilitacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CriarCarteiraHabilitacaoRequest {

    @NotEmpty
    private String numero;

    @NotNull
    private CategoriaCarteiraHabilitacao categoria;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

}
