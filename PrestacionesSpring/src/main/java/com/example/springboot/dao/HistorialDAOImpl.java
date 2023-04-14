package com.example.springboot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.example.springboot.model.Historial;
import com.example.springboot.model.Prestaciones;

public class HistorialDAOImpl implements HistorialDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public HistorialDAOImpl(javax.sql.DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
	}
	
	@Override
	public int insertar(Historial historial) {
		String sql = "INSERT INTO historial (dni_registro, fecha, accion, valoranterior) VALUES(?,?,?,?)";
		return jdbcTemplate.update(sql, historial.getDni_registro(), historial.getFecha(), historial.getAccion(), historial.getValoranterior());
	}

	@Override
	public List<Historial> mostrar(String dni_registro) {
		String sql = "SELECT * FROM historial WHERE dni_registro = '" + dni_registro + "' ORDER BY fecha DESC";
		
		RowMapper<Historial> rowMapper = new RowMapper<Historial>() {

			@Override
			public Historial mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dni_registro = rs.getString("dni_registro");
				Timestamp fecha = rs.getTimestamp("fecha");
				String accion = rs.getString("accion");
				String valoranterior = rs.getString("valoranterior");
				
				return new Historial(dni_registro, fecha, accion, valoranterior);
			}
		};
		
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	@Override
	public Historial mostrar1(String dni_registro) {
		String sql = "SELECT * FROM historial WHERE dni_registro = '" + dni_registro + "'";
		
		ResultSetExtractor<Historial> extractor = new ResultSetExtractor<Historial>() {
			@Override
			public Historial extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					
					String dni_registro = rs.getString("dni_registro");
					Timestamp fecha = rs.getTimestamp("fecha");
					String accion = rs.getString("accion");
					String valoranterior = rs.getString("valoranterior");
					
					return new Historial(dni_registro, fecha, accion, valoranterior);
				}
				return null;
			}
		};
		return jdbcTemplate.query(sql, extractor);
	}

	@Override
	public int eliminar(String dni) {
		String sql = "DELETE FROM historial WHERE dni_registro = '" + dni + "'";
		return jdbcTemplate.update(sql);
	}
}
