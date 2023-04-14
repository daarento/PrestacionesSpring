package com.example.springboot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.example.springboot.model.Historial;
import com.example.springboot.model.Prestaciones;

public class PrestacionesDAOImpl implements PrestacionesDAO {

	private JdbcTemplate jdbcTemplate;
	
	public PrestacionesDAOImpl(javax.sql.DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
	}
	
	@Override
	public int insertar(Prestaciones p) {
		if(p.getSeguridadsocial() != 0) {
			String sql = "INSERT INTO datos (seguridadsocial, dni, nombre, apellidos, provincia, calle, numero, codigopostal, iban, entidad, cuantia, atraso) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			return jdbcTemplate.update(sql, p.getSeguridadsocial(), p.getDni(), p.getNombre().toUpperCase(), p.getApellidos().toUpperCase(), p.getProvincia().toUpperCase(), p.getCalle().toUpperCase(), p.getNumero(), p.getCodigopostal(), p.getIban().toUpperCase(), p.getEntidad().toUpperCase(), p.getCuantia(), p.getAtraso());
		
		} else { //si solo contiene 3 cabeceras (dni, nombre y apellidos) insertar el registro en la base de datos como inactivo
			String sql = "INSERT INTO datos (dni, nombre, apellidos, inactivo) VALUES (?,?,?,?)";
			return jdbcTemplate.update(sql, p.getDni().toUpperCase(), p.getNombre().toUpperCase(), p.getApellidos().toUpperCase(), true);
		}
	}
	
	@Override
	public int actualizar(Prestaciones p) {
		String sql = "UPDATE datos SET seguridadsocial = ?, nombre = ?, apellidos = ?, provincia = ?, calle = ?, numero = ?, codigopostal = ?, iban = ?, entidad = ?, cuantia = ?, atraso = ? WHERE dni = ?";
		return jdbcTemplate.update(sql, p.getSeguridadsocial(), p.getNombre().toUpperCase(), p.getApellidos().toUpperCase(), p.getProvincia().toUpperCase(), p.getCalle().toUpperCase(), p.getNumero(), p.getCodigopostal(), p.getIban().toUpperCase(), p.getEntidad().toUpperCase(), p.getCuantia(), p.getAtraso(), p.getDni().toUpperCase());
	}

	@Override
	public Prestaciones mostrar(String dni) {
		String sql = "SELECT * FROM datos WHERE dni = '" + dni + "'";
		
		ResultSetExtractor<Prestaciones> extractor = new ResultSetExtractor<Prestaciones>() {
			@Override
			public Prestaciones extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					Long seguridadsocial = rs.getLong("seguridadsocial");
					String dni = rs.getString("dni");
					String nombre = rs.getString("nombre");
					String apellidos = rs.getString("apellidos");
					String provincia = rs.getString("provincia");
					String calle = rs.getString("calle");
					int numero = rs.getInt("numero");
					int codigopostal = rs.getInt("codigopostal");
					String iban = rs.getString("iban");
					String entidad = rs.getString("entidad");
					float cuantia = rs.getFloat("cuantia");
					float atraso = rs.getFloat("atraso");
					
					return new Prestaciones(seguridadsocial, dni, nombre, apellidos, provincia, calle, numero, codigopostal, iban, entidad, cuantia, atraso);
				}
				return null;
			}
		};
		return jdbcTemplate.query(sql, extractor);
	}

	@Override
	public int eliminar(String dni) { //queremos que cambie el campo inactivo, en vez de eliminar el registro
		/*String sql = "UPDATE datos SET inactivo = NOT inactivo WHERE dni = '" + dni + "'";
		return jdbcTemplate.update(sql);*/
		return 1;
	}

	@Override
	public List<Prestaciones> listar() {
		String sql = "SELECT * FROM datos order by inactivo ASC";
		
		RowMapper<Prestaciones> rowMapper = new RowMapper<Prestaciones>() {

			@Override
			public Prestaciones mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long seguridadsocial = rs.getLong("seguridadsocial");
				String dni = rs.getString("dni");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String provincia = rs.getString("provincia");
				String calle = rs.getString("calle");
				int numero = rs.getInt("numero");
				int codigopostal = rs.getInt("codigopostal");
				String iban = rs.getString("iban");
				String entidad = rs.getString("entidad");
				float cuantia = rs.getFloat("cuantia");
				float atraso = rs.getFloat("atraso");
				boolean inactivo = rs.getBoolean("inactivo");
				
				return new Prestaciones(seguridadsocial, dni, nombre, apellidos, provincia, calle, numero, codigopostal, iban, entidad, cuantia, atraso, inactivo);
			}
		};
		
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public int insertarHistorial(Historial historial) {
		String sql = "INSERT INTO historial (dni_registro, fecha, accion, valoranterior) VALUES(?,?,?,?)";
		return jdbcTemplate.update(sql, historial.getDni_registro(), historial.getFecha(), historial.getAccion(), historial.getValoranterior());
	}

	@Override
	public Prestaciones obtenerPrestacion(String dni) {
		Prestaciones prestaciones = mostrar(dni);
		return prestaciones;
	}

}
