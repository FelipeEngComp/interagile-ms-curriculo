package com.interagile.cliente.escola.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.CursoCadastroModel;
import com.interagile.cliente.escola.response.Response;
import com.interagile.cliente.escola.response.Response.ResponseBuilder;
import com.interagile.cliente.escola.service.ICursoService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/curso")
@CrossOrigin(origins = "*")
public class CursoController {

	private static final Logger LOG = LoggerFactory.getLogger(MateriaController.class);
	
	@Autowired
	private ICursoService cursoService;
	
	@PostMapping("/cadastrar")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	public ResponseEntity<Response<Boolean>> cadastrarCurso(@RequestBody CursoCadastroModel curso) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<Boolean> responseBuilder = Response.builder();
		try {
			Boolean cursoCadastrado = this.cursoService.cadastra(curso);
			responseBuilder.data(cursoCadastrado);
			responseBuilder.status(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		}catch (CurriculoException m) {
			responseBuilder.erros(Arrays.asList(m.getMessage()));
			responseBuilder.status(m.getHttpStatusCode());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		} catch (Exception e) {
			responseBuilder.erros(Arrays.asList(e.getMessage()));
			responseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		}
	}
}
