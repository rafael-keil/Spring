package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.mapper.CorridaMapper;
import br.com.cwi.crescer.melevaai.representation.request.CriarCorridaRequest;
import br.com.cwi.crescer.melevaai.representation.request.NotaRequest;
import br.com.cwi.crescer.melevaai.representation.response.CriarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.IniciarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodasCorridasResponse;
import br.com.cwi.crescer.melevaai.service.CorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/corridas")
public class CorridaController {

    @Autowired
    private CorridaService service;

    private static CorridaMapper mapper = new CorridaMapper();


    @PostMapping("/passageiros/{cpf}")
    @ResponseStatus(HttpStatus.CREATED)
    public CriarCorridaResponse chamarCorrida(@RequestBody @Valid CriarCorridaRequest request, @PathVariable String cpf) throws ValidacaoNegocioException, NaoCadastradoException {

        return service.criarCorrida(request, cpf);

    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public IniciarCorridaResponse iniciarCorrida(@PathVariable String id) throws NaoCadastradoException, CorridaIndisponivelException {

        return service.iniciarCorrida(id);

    }

    @GetMapping
    public ResponseEntity<List<ListarTodasCorridasResponse>> ListarTodasCorridas() {

        List<ListarTodasCorridasResponse> corridas = service.listarCorridas();

        if (corridas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(corridas);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void finalizaCorrida(@PathVariable String id) throws NaoCadastradoException, CorridaIndisponivelException, SemSaldoException {

        service.finalizarCorrida(id);

    }

    @PostMapping("/{idCorrida}/motoristas/avaliacao")
    @ResponseStatus(HttpStatus.OK)
    public void avaliarMotorista(@RequestBody NotaRequest nota, @PathVariable String idCorrida) throws ValidacaoNegocioException, JaAvaliadoException, CorridaIndisponivelException, NaoCadastradoException {

        service.avaliarMotorista(nota, idCorrida);

    }

    @PostMapping("/{idCorrida}/passageiros/avaliacao")
    @ResponseStatus(HttpStatus.OK)
    public void avaliarPassageiro(@RequestBody NotaRequest nota, @PathVariable String idCorrida) throws ValidacaoNegocioException, JaAvaliadoException, CorridaIndisponivelException, NaoCadastradoException {

        service.avaliarPassageiro(nota, idCorrida);

    }

    @GetMapping("/passageiros/{cpf}")
    public ResponseEntity<List<ListarTodasCorridasResponse>> listarTodasCorridasPassageiro(@PathVariable String cpf) throws NaoCadastradoException {

        List<ListarTodasCorridasResponse> listarTodasCorridasResponses = service.listarTodasCorridasPassageiro(cpf);

        if (listarTodasCorridasResponses.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(listarTodasCorridasResponses);
    }

    @GetMapping("/motoristas/{cpf}")
    public ResponseEntity<List<ListarTodasCorridasResponse>> listarTodasCorridasMotorista(@PathVariable String cpf) throws NaoCadastradoException {

        List<ListarTodasCorridasResponse> listarTodasCorridasResponses = service.listarTodasCorridasMotorista(cpf);

        if (listarTodasCorridasResponses.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(listarTodasCorridasResponses);
    }


}
