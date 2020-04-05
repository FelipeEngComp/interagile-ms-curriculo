package com.interagile.cliente.escola.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.model.dto.MateriaDTO;
import com.interagile.cliente.escola.response.Response;
import com.interagile.cliente.escola.response.Response.ResponseBuilder;
import com.interagile.cliente.escola.service.IMateriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Materia")
@RequestMapping("/materia")
@CrossOrigin(origins = "*")
public class MateriaController {

	private static final Logger LOG = LoggerFactory.getLogger(MateriaController.class);
	
	@Autowired
	private IMateriaService materiaService;
	
	@ApiOperation(value = "Consultar matéria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@GetMapping("/consultar/{codMateria}")
	public ResponseEntity<Response<MateriaDTO>> consultarMateriasCadastradas(@PathVariable String codMateria) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<MateriaDTO> responseBuilder = Response.builder();
		try {
			MateriaDTO matriculaCadastrada = this.materiaService.consultarMateriaCadastrada(StringUtils.upperCase(codMateria));
			responseBuilder.data(matriculaCadastrada);
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
	
	@ApiOperation(value = "Cadastrar matéria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@PostMapping("/cadastrar")
	public ResponseEntity<Response<Boolean>> cadastrarMateria(@RequestBody MateriaCadastroModel materia) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<Boolean> responseBuilder = Response.builder();
		try {
			Boolean matriculaCadastrada = this.materiaService.cadastrar(materia);
			responseBuilder.data(matriculaCadastrada);
			responseBuilder.status(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		}catch (CurriculoException m) {
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
	
	@ApiOperation(value = "Atualizar matéria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@PutMapping("/atualizar")
	public ResponseEntity<Response<Boolean>> atualizarMateria(@RequestBody MateriaCadastroModel materia) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<Boolean> responseBuilder = Response.builder();
		try {
			Boolean matriculaCadastrada = this.materiaService.atualizar(materia);
			responseBuilder.data(matriculaCadastrada);
			responseBuilder.status(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
		}catch (CurriculoException m) {
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
	
	
	@ApiOperation(value = "Listar matérias cadastradas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@GetMapping("/listar")
	public ResponseEntity<Response<List<MateriaDTO>>> listarMateriasCadastradas() {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<List<MateriaDTO>> responseBuilder = Response.builder();
		try {
			List<MateriaDTO>  matriculaCadastrada = this.materiaService.listar();
			responseBuilder.data(matriculaCadastrada);
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
