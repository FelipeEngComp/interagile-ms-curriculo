package com.interagile.cliente.escola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.interagile.cliente.escola.model.CursoDB;

public interface ICursoRepository extends JpaRepository<CursoDB, Long>{
	
	@Query("select c from CursoDB c where c.codigo =:codigo")
	public CursoDB findCursoByCodigo(@Param("codigo")String codigo);
	
}
