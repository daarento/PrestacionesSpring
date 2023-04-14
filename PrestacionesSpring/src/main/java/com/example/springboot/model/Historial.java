package com.example.springboot.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Historial {
	private int id;
	private String dni_registro, accion, valoranterior;
	private Timestamp fecha;
	private ArrayList<String> contenido = new ArrayList<>();
	
	public Historial() {}
	
	public Historial(int id, String dni_registro, String accion, String valoranterior) {
		this.id = id;
		this.dni_registro = dni_registro;
		
		//convertir la fecha actual a Timestamp
		LocalDateTime actual = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = actual.format(formatter).replace(".0", "");
        Timestamp timestamp = Timestamp.valueOf(formatDateTime);
        
        this.fecha = timestamp;
        //
		
		this.accion = accion;
		this.valoranterior = valoranterior;
	}
	
	public Historial(String dni_registro, Timestamp fecha, String accion, String valoranterior) {
		this.dni_registro = dni_registro;
		
        this.fecha = fecha;
		
		this.accion = accion;
		this.valoranterior = valoranterior;
	}
	
	public Historial(String dni_registro, String accion, String valoranterior) {
		this.dni_registro = dni_registro;
		
		//convertir la fecha actual a Timestamp
		LocalDateTime actual = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = actual.format(formatter).replace(".0", "");
		Timestamp timestamp = Timestamp.valueOf(formatDateTime);
		        
		this.fecha = timestamp;
		//
		
		this.accion = accion;
		this.valoranterior = valoranterior;
	}
	
	public Historial(String dni_registro, String accion, ArrayList<String> contenido) {
		this.dni_registro = dni_registro;
		
		//convertir la fecha actual a Timestamp
		LocalDateTime actual = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formatDateTime = actual.format(formatter).replace(".0", "");
		Timestamp timestamp = Timestamp.valueOf(formatDateTime);
		        
		this.fecha = timestamp;
		//
		
		this.accion = accion;
		
		this.contenido = contenido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDni_registro() {
		return dni_registro;
	}

	public void setDni_registro(String dni_registro) {
		this.dni_registro = dni_registro;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getValoranterior() {
		return valoranterior;
	}

	public void setValoranterior(String valoranterior) {
		this.valoranterior = valoranterior;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	public void setContenido(ArrayList<String> contenido) {
		this.contenido = contenido;
	}
	
	public ArrayList<String> getContenido(){
		return contenido;
	}
}
