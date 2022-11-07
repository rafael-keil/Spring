package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.JaCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.MotoristaComCNHVencidaException;
import br.com.cwi.crescer.melevaai.excepetion.ValidacaoNegocioException;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosMotoristasResponse;
import br.com.cwi.crescer.melevaai.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/publico/motoristas")
public class MotoristaPublicoController {

    @Autowired
    private MotoristaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarMotorista(@RequestBody @Valid CriarMotoristaRequest request) throws JaCadastradoException, ValidacaoNegocioException, MotoristaComCNHVencidaException {

        service.criarMotorista(request);

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

}
