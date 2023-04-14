package com.example.springboot.dao;

import java.util.List;

import com.example.springboot.model.Historial;
import com.example.springboot.model.Prestaciones;

/*
 * DAO (data access object) es un componente de software que suministra 
 * una interfaz común entre la aplicación y uno o más dispositivos de 
 * almacenamiento de datos, tales como una Base de datos o un archivo.
 */

public interface PrestacionesDAO { 
	public int insertar(Prestaciones prestaciones);
	
	public int insertarHistorial(Historial historial);
	
	public int actualizar(Prestaciones prestaciones);
	
	public Prestaciones mostrar(String dni);
	
	public int eliminar(String dni);
	
	public List<Prestaciones> listar();

	public Prestaciones obtenerPrestacion(String dni);
}
