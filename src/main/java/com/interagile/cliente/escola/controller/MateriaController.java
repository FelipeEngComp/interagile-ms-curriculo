package com.interagile.cliente.escola.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interagile.cliente.escola.exception.MateriaException;
import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.service.IMateriaService;
import com.interagile.cliente.template.escola.Response;
import com.interagile.cliente.template.escola.Response.ResponseBuilder;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/materia")
@CrossOrigin(origins = "*")
public class MateriaController {

	private static final Logger LOG = LoggerFactory.getLogger(MateriaController.class);
	
	@Autowired
	private IMateriaService materiaService;
	
	@GetMapping("/suaRota/{path}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	public ResponseEntity<Response<MateriaCadastroModel>> consultarMateriasCadastradas(@PathVariable String path) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<MateriaCadastroModel> responseBuilder = Response.builder();
		responseBuilder.data(null);
		responseBuilder.status(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
	}

	@PostMapping("/cadastrar")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	public ResponseEntity<Response<Boolean>> cadastrarMateria(@RequestBody MateriaCadastroModel materia) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<Boolean> responseBuilder = Response.builder();
		try {
			Boolean matriculaCadastrada = materiaService.cadastro(materia);
			responseBuilder.data(matriculaCadastrada);
			responseBuilder.status(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		}catch (MateriaException m) {
			responseBuilder.data(false);
			responseBuilder.erros(Arrays.asList(m.getMessage()));
			responseBuilder.status(m.getHttpStatusCode());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		} catch (Exception e) {
			responseBuilder.data(false);
			responseBuilder.erros(Arrays.asList(e.getMessage()));
			responseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		}
	}
}
