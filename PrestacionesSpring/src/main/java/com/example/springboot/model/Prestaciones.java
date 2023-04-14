package com.example.springboot.model;

public class Prestaciones {
	private long seguridadsocial;
	private int numero, codigopostal;
	private String dni, nombre, apellidos, provincia, calle, iban, entidad;
	private float cuantia, atraso;
	private boolean inactivo = false;
	private Integer id;
	
	public Prestaciones() {}
	
	public Prestaciones(long seguridadsocial, String dni, String nombre, String apellidos, String provincia, String calle, int numero, int codigopostal, String iban, String entidad, float cuantia, float atraso, boolean inactivo) {
		this.seguridadsocial = seguridadsocial;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.provincia = provincia;
		this.calle = calle;
		this.numero = numero;
		this.codigopostal = codigopostal;
		this.iban = iban;
		this.entidad = entidad;
		this.cuantia = cuantia;
		this.atraso = atraso;
		this.inactivo = inactivo;
	}
	
	//constructor con seguridadsocial
	public Prestaciones(long seguridadsocial, String dni, String nombre, String apellidos, String provincia, String calle, int numero, int codigopostal, String iban, String entidad, float cuantia, float atraso) {
		this.seguridadsocial = seguridadsocial;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.provincia = provincia;
		this.calle = calle;
		this.numero = numero;
		this.codigopostal = codigopostal;
		this.iban = iban;
		this.entidad = entidad;
		this.cuantia = cuantia;
		this.atraso = atraso;
		this.inactivo = false;
	}
	
	//constructor sin seguridadsocial
	public Prestaciones(String dni, String nombre, String apellidos){
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.inactivo = true;
	}
	
	public long getSeguridadsocial() {
		return seguridadsocial;
	}
	
	public void setSeguridadsocial(long seguridadsocial) {
		this.seguridadsocial = seguridadsocial;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getCodigopostal() {
		return codigopostal;
	}
	
	public void setCodigopostal(int codigopostal) {
		this.codigopostal = codigopostal;
	}
	
	public float getCuantia() {
		return cuantia;
	}
	
	public void setCuantia(float cuantia) {
		this.cuantia = cuantia;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getCalle() {
		return calle;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String getEntidad() {
		return entidad;
	}
	
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	public float getAtraso() {
		return atraso;
	}
	
	public void setAtraso(float atraso) {
		this.atraso = atraso;
	}	
	
	public void setInactivo(boolean inactivo) {
		this.inactivo = inactivo;
	}
	
	public boolean isInactivo() {
		return inactivo;
	}
	
	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return getSeguridadsocial() + "," + getDni() + "," + getNombre() + "," + getApellidos()
		+ "," + getProvincia() + "," + getCalle() + "," + getNumero() + "," + getCodigopostal()
		+ "," + getIban() + "," + getEntidad() + "," + getCuantia() + "," + getAtraso();
	}
}
