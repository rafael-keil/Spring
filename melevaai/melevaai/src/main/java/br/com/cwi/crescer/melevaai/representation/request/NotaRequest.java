package br.com.cwi.crescer.melevaai.representation.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotaRequest {

    @NotNull
    private Integer nota;

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
