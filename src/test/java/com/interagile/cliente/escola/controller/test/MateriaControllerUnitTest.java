package com.interagile.cliente.escola.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.response.Response;
import com.interagile.cliente.escola.service.IMateriaService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MateriaControllerUnitTest {

	@MockBean
	private IMateriaService materiaService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testConsultaCadastroUsuarioSucesso() {
		MateriaCadastroModel materiaCadastro = new MateriaCadastroModel();
		
		materiaCadastro.setNome("Sinais e sistemas");
		materiaCadastro.setFrequencia(1);
		materiaCadastro.setHoras(68);
		materiaCadastro.setCodigo("SINSIS");
		
		Mockito.when(materiaService.consultarMateriaCadastrada(StringUtils.upperCase("CodMat123"))).thenReturn(materiaCadastro);
		
		ResponseEntity<Response<MateriaCadastroModel>> materia = restTemplate.withBasicAuth("interagile", "mscurriculo").exchange(
				"http://localhost:" + this.port + "/materia/consultar/CodMat123",
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<MateriaCadastroModel>>() {
				});

		assertNotNull(materia);
		assertEquals(200, materia.getStatusCode().value());
	}
	
	@Test
	public void testConsultaCadastroUsuarioCurriculoException() {

		Mockito.when(materiaService.consultarMateriaCadastrada(StringUtils.upperCase("CodMat123"))).thenThrow(new CurriculoException("Error",HttpStatus.BAD_REQUEST.value()));

		ResponseEntity<Response<MateriaCadastroModel>> materia = restTemplate.withBasicAuth("interagile", "mscurriculo").exchange(
				"http://localhost:" + this.port + "/materia/consultar/CodMat123",
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<MateriaCadastroModel>>() {
				});

		assertEquals(400, materia.getBody().getStatus());
	}
	
	@Test
	public void testConsultaCadastroUsuarioException() {

		Mockito.when(materiaService.consultarMateriaCadastrada(StringUtils.upperCase("CodMat123"))).thenThrow(new RuntimeException());

		ResponseEntity<Response<MateriaCadastroModel>> materia = restTemplate.withBasicAuth("interagile", "mscurriculo").exchange(
				"http://localhost:" + this.port + "/materia/consultar/CodMat123",
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<MateriaCadastroModel>>() {
				});

		assertEquals(500, materia.getBody().getStatus());
	}

}
