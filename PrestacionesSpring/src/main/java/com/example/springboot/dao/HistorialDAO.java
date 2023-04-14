package com.example.springboot.dao;

import java.util.List;

import com.example.springboot.model.Historial;
import com.example.springboot.model.Prestaciones;

public interface HistorialDAO {
	public int insertar(Historial historial);
	
	public List<Historial> mostrar(String dni);
	
	public Historial mostrar1(String dni);
	
	public int eliminar(String dni);
}
