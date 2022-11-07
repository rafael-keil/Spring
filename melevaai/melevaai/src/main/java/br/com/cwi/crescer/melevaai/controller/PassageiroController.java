package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.JaCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.NaoCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.SemSaldoException;
import br.com.cwi.crescer.melevaai.excepetion.ValidacaoNegocioException;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;
import br.com.cwi.crescer.melevaai.representation.request.ValorRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosPassageirosResponse;
import br.com.cwi.crescer.melevaai.representation.response.PassageiroResponse;
import br.com.cwi.crescer.melevaai.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/passageiros")
public class PassageiroController {

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

    @PutMapping("/{cpf}/conta-virtual")
    @ResponseStatus(HttpStatus.CREATED)
    private void incluirCredito(@RequestBody @Valid ValorRequest request, @PathVariable String cpf) throws SemSaldoException, NaoCadastradoException {

        service.depositarCredito(request.getValor(), cpf);

    }

    @GetMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    private PassageiroResponse getPassageiro(@PathVariable String cpf) throws NaoCadastradoException {

        return service.consultarPassageiroResponsePorCpf(cpf);

    }

}
