package com.interagile.cliente.escola.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interagile.cliente.escola.response.Response;
import com.interagile.cliente.escola.response.Response.ResponseBuilder;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/suaController")
@CrossOrigin(origins = "*")
public class CursoController {

	private static final Logger LOG = LoggerFactory.getLogger(CursoController.class);

	@GetMapping("/suaRota/{path}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	public ResponseEntity<Response<Object>> consultaTemplate(@PathVariable String path) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<Object> responseBuilder = Response.builder();
		responseBuilder.data(path);
		responseBuilder.status(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
	}
}
