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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interagile.cliente.escola.dao.CursoDAO;
import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.CursoCadastroModel;
import com.interagile.cliente.escola.response.Response;
import com.interagile.cliente.escola.response.Response.ResponseBuilder;
import com.interagile.cliente.escola.service.ICursoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Curso")
@RequestMapping("/curso")
@CrossOrigin(origins = "*")
public class CursoController {

	private static final Logger LOG = LoggerFactory.getLogger(MateriaController.class);
	
	@Autowired
	private ICursoService cursoService;
	
	@ApiOperation(value = "Cadastrar curso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@PostMapping("/cadastrar")
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
	
	@ApiOperation(value = "Atualizar curso")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@PutMapping("/atualizar")
	public ResponseEntity<Response<Boolean>> atualizarCurso(@RequestBody CursoCadastroModel curso) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<Boolean> responseBuilder = Response.builder();
		try {
			Boolean cursoCadastrado = this.cursoService.atualizar(curso);
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
	
	@ApiOperation(value = "Listar cursos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@GetMapping("/listar")
	public ResponseEntity<Response<List<CursoDAO>>> listarCursos() {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<List<CursoDAO>> responseBuilder = Response.builder();
		try {
			List<CursoDAO> cursoCadastrados = this.cursoService.listarCursos();
			responseBuilder.data(cursoCadastrados);
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
	
	@ApiOperation(value = "Consultar curso por código cadastrado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso na requisição"),
			@ApiResponse(code = 400, message = "Erro na requisição") })
	@GetMapping("/consultar/{codigo}")
	public ResponseEntity<Response<CursoDAO>> consultarCursoPorCod(@PathVariable String codigo) {
		LOG.debug("Iniciando a controller");
		ResponseBuilder<CursoDAO> responseBuilder = Response.builder();
		try {
			CursoDAO cursoCadastrados = this.cursoService.consultaPorCod(codigo);
			responseBuilder.data(cursoCadastrados);
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
