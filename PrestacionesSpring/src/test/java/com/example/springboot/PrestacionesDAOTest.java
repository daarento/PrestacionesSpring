package com.example.springboot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.activation.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.springboot.dao.PrestacionesDAO;
import com.example.springboot.dao.PrestacionesDAOImpl;
import com.example.springboot.model.Prestaciones;

/*
 * Probar las query, añadir las etiquetas @Text y luego en Outline > boton derecho > Run As > JUnit Test
 */

class PrestacionesDAOTest {

	private DriverManagerDataSource dataSource;
	private PrestacionesDAO dao;
	
	//Para que realice esto antes de cada método. Crear la conexion con la base de datos
	@BeforeEach
	void setupBeforeEach() {
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/banco");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		
		dao = new PrestacionesDAOImpl((javax.sql.DataSource) dataSource);
	}
	
	@Test
	void testInsertar() { //funciona
		Prestaciones prestaciones = new Prestaciones(456566327655L, "41470000H", "MORSA", "EXTINTA", "GC", "ATLANTIS", 77, 20008, "ES0700813043350000000003", "MAR", 8, 4);
		int result = dao.insertar(prestaciones);
		
		assertTrue(result > 0);
	}

	@Test
	void textActualizar() {	//funciona
		Prestaciones prestaciones = new Prestaciones(400000000005L, "41470000H", "MORSITA", "VIVA", "LPGC", "JARDINES ATLANTIS", 88, 21008, "ES0700813043355562500003", "ATLANTICO", 108, 50);
		int result = dao.actualizar(prestaciones);
		
		assertTrue(result > 0);
	}
	
	@Test
	void textMostrar() { //funciona
		String dni = "41475320H";
		Prestaciones prestaciones = dao.mostrar(dni);
		
		if(prestaciones != null) {
			System.out.println("\nRegistro encontrado: " + prestaciones);
		}
		
		assertNotNull(prestaciones);
	}
	
	@Test
	void textEliminar() { //funciona
		String dni = "41475320H";
		int result = dao.eliminar(dni);
		
		if(result > 0) {
			Prestaciones prestaciones = dao.mostrar(dni);
			System.out.println(dni + ", inactividad modificada.");
		}
		
		assertTrue(result > 0);
	}
	
	@Test
	void textListar() {
		List<Prestaciones> listarPrestaciones = dao.listar();
		
		for(Prestaciones cada: listarPrestaciones) {
			System.out.println("\n" + cada);
		}
		
		assertTrue(!listarPrestaciones.isEmpty());
	}

}
