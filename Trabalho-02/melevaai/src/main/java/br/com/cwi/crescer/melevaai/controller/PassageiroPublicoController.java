package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.JaCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.ValidacaoNegocioException;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosPassageirosResponse;
import br.com.cwi.crescer.melevaai.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/publico/passageiros")
public class PassageiroPublicoController {

    @Autowired
    private PassageiroService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarPassageiro(@RequestBody @Valid CriarPassageiroRequest request) throws JaCadastradoException, ValidacaoNegocioException {

        service.criarPassageiro(request);

    }

    @GetMapping
    private ResponseEntity<List<ListarTodosPassageirosResponse>> todosPassageiros() {
        List<ListarTodosPassageirosResponse> listarTodosPassageirosRespons = service.listarPassageiros();

        if (listarTodosPassageirosRespons.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listarTodosPassageirosRespons);
    }

}
