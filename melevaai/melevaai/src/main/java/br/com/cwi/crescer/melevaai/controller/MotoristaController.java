package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.request.ValorRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosMotoristasResponse;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import br.com.cwi.crescer.melevaai.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarMotorista(@RequestBody @Valid CriarMotoristaRequest request) throws JaCadastradoException, ValidacaoNegocioException, MotoristaComCNHVencidaException {

        service.criarMotorista(request);

    }

    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public void excluirMotorista(@PathVariable String cpf) throws NaoCadastradoException, MotoristaComVeiculoException {

        service.excluirMotorista(cpf);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ListarTodosMotoristasResponse>> todosMotoristas() {

        List<ListarTodosMotoristasResponse> listarTodosMotoristasRespons = service.listarMotoristas();

        if (listarTodosMotoristasRespons.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listarTodosMotoristasRespons);
    }

    @PutMapping("/{cpf}/conta-virtual")
    private void sacarCredito(@RequestBody @Valid ValorRequest request, @PathVariable String cpf) throws SemSaldoException, NaoCadastradoException {

        service.sacarCredito(request.getValor(), cpf);

    }


    @GetMapping("/{cpf}")
    private MotoristaResponse getMotorista(@PathVariable String cpf) throws NaoCadastradoException {

        return service.consultarMotoristaResponsePorCpf(cpf);

    }

}
