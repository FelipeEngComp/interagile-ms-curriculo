package com.interagile.cliente.escola.service.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interagile.cliente.escola.exception.CurriculoException;
import com.interagile.cliente.escola.model.MateriaCadastroModel;
import com.interagile.cliente.escola.model.MateriaDB;
import com.interagile.cliente.escola.repository.IMateriaRepository;
import com.interagile.cliente.escola.service.MateriaService;

@ExtendWith(MockitoExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MateriaServiceTest {

	// explicar a diferenca entre mockBean e mock
	@Mock
	private IMateriaRepository materiaRepositoryMock;

	@InjectMocks
	private MateriaService materiaService;

	private MateriaDB materiaDao;

	private MateriaCadastroModel materia;

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);

		materia = new MateriaCadastroModel();
		
		materia.setNome("Sinais e sistemas");
		materia.setFrequencia(1);
		materia.setHoras(68);
		materia.setCodigo("SINSIS");

		materiaDao = new MateriaDB();
		materiaDao.setNome(StringUtils.upperCase(materia.getNome()));
		materiaDao.setCodigo(StringUtils.upperCase(materia.getCodigo()));
		materiaDao.setFrequencia(materia.getFrequencia());
		materiaDao.setHoras(materia.getHoras());

	}

	@Test
	public void cadastrarMateriaSucesso() {

		Mockito.when(this.materiaRepositoryMock.save(this.materiaDao)).thenReturn(this.materiaDao);
		Mockito.when(this.materiaRepositoryMock.findMateriaByCodigo(this.materia.getCodigo())).thenReturn(null);

		assertTrue(this.materiaService.cadastrar(this.materia));

		Mockito.verify(materiaRepositoryMock, times(1)).save(this.materiaDao);
		Mockito.verify(materiaRepositoryMock, times(1)).findMateriaByCodigo(this.materia.getCodigo());
	}

	@Test
	public void cadastrarMateriaCurriculoNullPointerException() {
		
		Mockito.when(this.materiaRepositoryMock.findMateriaByCodigo(this.materia.getCodigo())).thenReturn(null);

		Assertions.assertThrows(RuntimeException.class, () -> {
			this.materiaService.cadastrar(null);
		});
		Mockito.verify(materiaRepositoryMock, times(0)).save(this.materiaDao);
		Mockito.verify(materiaRepositoryMock, times(0)).findMateriaByCodigo(null);

	}

	@Test
	public void cadastrarMateriaCurriculoException() {
		
		Mockito.when(this.materiaRepositoryMock.findMateriaByCodigo(this.materia.getCodigo())).thenReturn(new MateriaDB());

		this.materia = new MateriaCadastroModel();
		
		materia.setNome("Sinais e sistemas");
		materia.setFrequencia(1);
		materia.setHoras(68);
		materia.setCodigo("SINSIS");
		
		this.materiaDao = new MateriaDB();
		this.materiaDao.setNome(StringUtils.upperCase(materia.getNome()));
		this.materiaDao.setCodigo(StringUtils.upperCase(materia.getCodigo()));
		this.materiaDao.setFrequencia(materia.getFrequencia());
		this.materiaDao.setHoras(materia.getHoras());

		Assertions.assertThrows(CurriculoException.class, () -> {
			this.materiaService.cadastrar(this.materia);
		});
		Mockito.verify(materiaRepositoryMock, times(0)).save(this.materiaDao);
		Mockito.verify(materiaRepositoryMock, times(1)).findMateriaByCodigo(this.materia.getCodigo());
	}
	
	@Test
	public void cadastrarMateriaCurriculoCurriculoCodigoJaCadastradoException() {
		
		Mockito.when(this.materiaRepositoryMock.findMateriaByCodigo(this.materia.getCodigo())).thenReturn(this.materiaDao);

		Assertions.assertThrows(CurriculoException.class, () -> {
			this.materiaService.cadastrar(this.materia);
		});
		
		Mockito.verify(materiaRepositoryMock, times(0)).save(this.materiaDao);
		Mockito.verify(materiaRepositoryMock, times(1)).findMateriaByCodigo(this.materia.getCodigo());
	}

}
