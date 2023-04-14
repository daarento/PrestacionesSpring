package com.example.springboot.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.springboot.dao.HistorialDAO;
import com.example.springboot.dao.PrestacionesDAO;
import com.example.springboot.model.Historial;
import com.example.springboot.model.Prestaciones;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@Controller
public class MainController {
	
	private static final long serialVersionUID = 1L;
	
	BufferedReader br;
	private String linea;
	private String partes[];
	
	String dni = "";
	
	MiConexion miConexion = new MiConexion();
	Connection con = miConexion.getConnection();
	
	double totalAtraso = 0;
	double totalCuantia = 0;
	
	double totalAtrasoActivo = 0;
	double totalCuantiaActivo = 0;
	
	@Autowired
	private PrestacionesDAO prestacionesDAO;
	
	@Autowired
	private HistorialDAO historialDAO;
	
	@Autowired
    private HttpServletRequest request;
	
	//MOSTRAR TODOS LOS ELEMENTOS DE LA BASE DE DATOS
	@RequestMapping(value = "/")
	public ModelAndView listarPrestaciones(ModelAndView model) {
		List<Prestaciones> listado = prestacionesDAO.listar();
		totalCuantiaActivo = 0;
		totalAtrasoActivo = 0;
		totalAtraso = 0;
		totalCuantia = 0;
		
		//recoger el total de atrasos
		//double totalAtraso = 0;
		for(Prestaciones cada: listado) {
			totalAtraso += cada.getAtraso();
		}
			
		//recoger el total de cuantias
		//double totalCuantia = 0;
		for(Prestaciones cada: listado) {
			totalCuantia += cada.getCuantia();
		}
					
		//recoger el total de atrasos (SOLO ACTIVOS)
		//double totalAtrasoActivo = 0;
		for(Prestaciones cada: listado) {
			if(!cada.isInactivo()) { //si esta inactivo
				totalAtrasoActivo += cada.getAtraso();
			}
		}
					
		//recoger el total de cuantía (SOLO ACTIVOS)
		//double totalCuantiaActivo = 0;
		for(Prestaciones cada: listado) {
			if(!cada.isInactivo()) { //si esta inactivo
				totalCuantiaActivo += cada.getCuantia();
			}
		}
		
		model.addObject("listadoPrestaciones", listado);
		model.addObject("totalcuantiaactivos", totalCuantiaActivo);
		model.addObject("totalatrasoactivos", totalAtrasoActivo);
		model.addObject("totalcuantia", totalCuantia);
		model.addObject("totalatraso", totalAtraso);
		model.setViewName("index"); //nombre del archivo .jsp
		return model;
	}
	
	//RECOGER LOS VALORES INTRODUCIDOS EN EL FORMULARIO (formPrestacion) EN LA CLASE PRESTACIONES (prest)
	@RequestMapping(value = "/formulario", method = RequestMethod.GET)
	public ModelAndView nuevaPrestacion(ModelAndView model) {
				
		Prestaciones prest = new Prestaciones();
		model.addObject("formPrestacion", prest);	
		model.setViewName("formulario");
		return model;
	}
	
	//REALIZAR LA INSERCION EN LA BASE DE DATOS, LLAMANDO AL METODO insertar(); Y REDIRECCIONANDO AL INDEX
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ModelAndView guardarPrestacion(@ModelAttribute Prestaciones pres) throws SQLException, ClassNotFoundException {
		
		if(!pres.getDni().isBlank()) {
			if(!pres.getDni().equalsIgnoreCase(dni)) {
				prestacionesDAO.insertar(pres);
				System.out.println("Registro con DNI: '" + dni + "' insertado.");
			} else {
				
				PreparedStatement ps = null;
		        ps = con.prepareStatement("SELECT * FROM datos WHERE dni = ?");
				ps.setString(1, dni);
				ResultSet rs = ps.executeQuery();
				Prestaciones valorAntiguo = null;
				if(rs.next()) {
					valorAntiguo = prestacionesDAO.mostrar(dni);
				}
				//
				
				prestacionesDAO.actualizar(pres);
				System.out.println("Registro con DNI: '" + dni + "' actualizado.");
				
				//
				//recoger el nombre antes de modificar
				ps = con.prepareStatement("SELECT * FROM datos WHERE dni = ?");
				ps.setString(1, dni);
				ResultSet rs2 = ps.executeQuery();
				Prestaciones valorActual = null;
				if(rs2.next()) {
					valorActual = prestacionesDAO.mostrar(dni);
				}
				//
				
				StringBuilder sb = new StringBuilder();
				String cambio = "";
				
				//Si el dni es diferente se almacena en el arraylist
				if(valorAntiguo.getSeguridadsocial() != (valorActual.getSeguridadsocial())){ 
					sb.append("Seg. Social: " + valorAntiguo.getSeguridadsocial() + ",");
					System.out.println("Seguridad Social modificada.");
				}
				//Si el nombre es diferente
				if(!valorAntiguo.getNombre().equals(valorActual.getNombre())){
					sb.append("Nombre: " + valorAntiguo.getNombre().trim() + ",");
					System.out.println("Nombre modificado. " + valorAntiguo.getNombre());
					
				}
				//Si los apellidos son diferentes
				if(!valorAntiguo.getApellidos().equalsIgnoreCase(valorActual.getApellidos())){
					sb.append("Apellidos: " + valorAntiguo.getApellidos().trim() + ",");
					System.out.println("Apellidos modificados.");
				}
				//Si la provincia es diferente
				if(!valorAntiguo.getProvincia().equalsIgnoreCase(valorActual.getProvincia())){
					sb.append("Provincia: " + valorAntiguo.getProvincia().trim() + ",");
					System.out.println("Provincia modificada.");
				}
				//Si la calle es diferente
				if(!valorAntiguo.getCalle().equalsIgnoreCase(valorActual.getCalle())){
					sb.append("Calle: " + valorAntiguo.getCalle().trim() + ",");
					System.out.println("Calle modificada.");
				}
				//Si el número es diferente
				if(valorAntiguo.getNumero() != (valorActual.getNumero())){
					sb.append("Número: " + valorAntiguo.getNumero() + ",");
					System.out.println("Número modificado.");
				}
				//Si el código postal es diferente
				if(valorAntiguo.getCodigopostal() != (valorActual.getCodigopostal())){
					sb.append("Cód. Postal: " + valorAntiguo.getCodigopostal() + ",");
					System.out.println("Código postal modificado.");
				}
				//Si el iban es diferente
				if(!valorAntiguo.getIban().equalsIgnoreCase(valorActual.getIban())){
					sb.append("IBAN: " + valorAntiguo.getIban().trim() + ",");
					System.out.println("IBAN modificado.");
				}
				//Si el entidad es diferente
				if(!valorAntiguo.getEntidad().equalsIgnoreCase(valorActual.getEntidad())){
					sb.append("Entidad: " + valorAntiguo.getEntidad().trim() + ",");
					System.out.println("Entidad modificada.");
				}
				//Si la cuantía postal es diferente
				if(valorAntiguo.getCuantia() != (valorActual.getCuantia())){
					sb.append("Cuantía: " + valorAntiguo.getCuantia() + ",");
					System.out.println("Cuantía modificada.");
				}
				//Si el atraso postal es diferente
				if(valorAntiguo.getAtraso() != (valorActual.getAtraso())){
					sb.append("Atraso: " + valorAntiguo.getAtraso() + ",");
					System.out.println("Atraso modificado.");
				}
				
				String resultado = sb.toString();
				if(resultado.endsWith(",")) {
					resultado = resultado.substring(0, resultado.length() - 1); //si acaba en "," procedemos a quitarla
				}
				
				//Guardar en el Historial
	    		Historial historial = new Historial(dni, "MODIFICAR", resultado);
	    		
	    		if(resultado.length() == 0) {
	    			System.out.println("No hay modificaciones.");
	    		}
	    		
	    		else if(historialDAO.insertar(historial) > 0) {
	    			System.out.println("Historial insertado. Acción: MODIFICAR.");
	    		}
			}
		}
		
		return new ModelAndView("redirect:/");
	}
	
	//EDITAR REGISTRO Y LISTAR HISTORIAL
	@RequestMapping(value = "/formulario", params = "accion=editar", method = RequestMethod.GET)
	public ModelAndView actualizarPrestacion(HttpServletRequest request) {
		dni = request.getParameter("dni");
		
		Prestaciones prest = prestacionesDAO.mostrar(dni);
		ModelAndView model = new ModelAndView("formulario");
		model.addObject("formPrestacion", prest);
		System.out.println("dni long: " + dni.length() + " contenido: " + dni);
		
		List<Historial> historial = historialDAO.mostrar(dni);
		model.addObject("listaHistorial", historial);
		System.out.println("\nHistorial:\n" + historial);
		
		return model;
	}
	
	//ELIMINAR (ACTIVO / INACTIVO)
	@RequestMapping(value = "/estado", method = RequestMethod.GET)
	public ModelAndView estadoPrestacion(@RequestParam String dni) {
		
		//
		PreparedStatement ps = null;
		try {
	        ps = con.prepareStatement("SELECT inactivo FROM datos WHERE dni = ?");
	        ps.setString(1, dni);
	        
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				boolean inactivo = rs.getBoolean("inactivo"); //recogemos el valor booleano de la tabla inactivo
				if(!inactivo) {
					PreparedStatement psFalse = con.prepareStatement("UPDATE datos SET inactivo = ? WHERE dni = ?");
					psFalse.setBoolean(1, true);
					psFalse.setString(2, dni);
					psFalse.executeUpdate();
					
					PreparedStatement ps2 = con.prepareStatement("SELECT inactivo FROM datos WHERE dni = ?");
					ps2.setString(1, dni);
					ResultSet rs2 = ps2.executeQuery();
					
					boolean inactivoActualizado = false;
					if(rs2.next()) {
						inactivoActualizado = rs2.getBoolean("inactivo"); 
					}
					
					//recoger el nombre antes de modificar
					ps = con.prepareStatement("SELECT * FROM datos WHERE dni = ?");
					ps.setString(1, dni);
					ResultSet rs3 = ps.executeQuery();
					Prestaciones valorAntiguo = null;
					if(rs3.next()) {
						valorAntiguo = prestacionesDAO.obtenerPrestacion(dni);
					}
					//
					
					//Guardar en el Historial
		    		Historial historial = new Historial(dni, "DESACTIVADO", "Se ha desactivado el registro.");
		    		if(historialDAO.insertar(historial)>0) {
		    			System.out.println("Historial insertado. Acción: DESACTIVADO.");
		    		}
		    		//
					
					System.out.println("Se ha desactivado el registro '" + dni + "'. Estado actualizado > " +  inactivoActualizado);

				} else {
					PreparedStatement psTrue = con.prepareStatement("UPDATE datos SET inactivo = ? WHERE dni = ?");
					psTrue.setBoolean(1, false);
					psTrue.setString(2, dni);
					psTrue.executeUpdate();
					
					PreparedStatement ps2 = con.prepareStatement("SELECT inactivo FROM datos WHERE dni = ?");
					ps2.setString(1, dni);
					ResultSet rs2 = ps2.executeQuery();
					
					boolean inactivoActualizado = false;
					if(rs2.next()) {
						inactivoActualizado = rs2.getBoolean("inactivo"); 
					    //request.setAttribute("estado", inactivoActualizado); 
					}
					
					//recoger el nombre antes de modificar
					ps = con.prepareStatement("SELECT * FROM datos WHERE dni = ?");
					ps.setString(1, dni);
					ResultSet rs3 = ps.executeQuery();
					Prestaciones valorAntiguo = null;
					if(rs3.next()) {
						valorAntiguo = prestacionesDAO.obtenerPrestacion(dni);
					}
					//
					
					//Guardar en el Historial
		    		Historial historial = new Historial(dni, "ACTIVADO", "Se ha activado el registro.");
		    		if(historialDAO.insertar(historial)>0) {
		    			System.out.println("Historial insertado. Acción: ACTIVADO.");
		    		}
		    		//
					
					
					System.out.println("Se ha activado el registro '" + dni + "'. Estado actualizado > " +  inactivoActualizado);
					//request.getSession().setAttribute("actividad", inactivoActualizado);
				}
			}
		}
		catch(Exception ex) {}
		//
		
		prestacionesDAO.eliminar(dni);
		System.out.println(dni + ": actividad actualizada.");
		
		
		return new ModelAndView("redirect:/");
	}
	
	//DESCARGAR XML
	@RequestMapping(value = "/descargar", method = RequestMethod.POST)
	public ModelAndView descargarPrestacion(HttpServletRequest request,
	        HttpServletResponse response) {
		
		try {
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM datos");
	         
	         DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	         Document doc = docBuilder.newDocument();
	         Element rootElement = doc.createElement("prestaciones");
	         doc.appendChild(rootElement);
	         
	         while (rs.next()) {
	        	boolean inactivo = rs.getBoolean("inactivo"); //recogemos el valor de la tabla de la bbdd "inactivo"
	        	
	        	if(!inactivo) { //si el registro tiene valor inactivo = false, genera el xml
	        		Element registro = doc.createElement("registro");
		            rootElement.appendChild(registro);
		            
		            Element seguridadsocial = doc.createElement("seguridadsocial");
		            seguridadsocial.appendChild(doc.createTextNode(rs.getString("seguridadsocial")));
		            registro.appendChild(seguridadsocial);
		            
		            Element dni = doc.createElement("dni");
		            dni.appendChild(doc.createTextNode(rs.getString("dni")));
		            registro.appendChild(dni);
		            
		            Element nombre = doc.createElement("nombre");
		            nombre.appendChild(doc.createTextNode(rs.getString("nombre")));
		            registro.appendChild(nombre);
		            
		            Element apellidos = doc.createElement("apellidos");
		            apellidos.appendChild(doc.createTextNode(rs.getString("apellidos")));
		            registro.appendChild(apellidos);
		            
		            Element provincia = doc.createElement("provincia");
		            provincia.appendChild(doc.createTextNode(rs.getString("provincia")));
		            registro.appendChild(provincia);
		            
		            Element calle = doc.createElement("calle");
		            calle.appendChild(doc.createTextNode(rs.getString("calle")));
		            registro.appendChild(calle);
		            
		            Element numero = doc.createElement("numero");
		            numero.appendChild(doc.createTextNode(rs.getString("numero")));
		            registro.appendChild(numero);
		            
		            Element codigopostal = doc.createElement("codigopostal");
		            codigopostal.appendChild(doc.createTextNode(rs.getString("codigopostal")));
		            registro.appendChild(codigopostal);
		            
		            Element iban = doc.createElement("iban");
		            iban.appendChild(doc.createTextNode(rs.getString("iban")));
		            registro.appendChild(iban);
		            
		            Element entidad = doc.createElement("entidad");
		            entidad.appendChild(doc.createTextNode(rs.getString("entidad")));
		            registro.appendChild(entidad);
		            
		            Element cuantia = doc.createElement("cuantia");
		            cuantia.appendChild(doc.createTextNode(rs.getString("cuantia")));
		            registro.appendChild(cuantia);
		            
		            Element atraso = doc.createElement("atraso");
		            atraso.appendChild(doc.createTextNode(rs.getString("atraso")));
		            registro.appendChild(atraso);
	        	}
	         }
	         
	         String home = System.getProperty("user.home") + File.separator + "Downloads";
	 		 String path = home + "prestaciones.xml";	
	 		 
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File(path));
	         transformer.transform(source, result);
	         
	         System.out.println("Los registros se han almacenado en el archivo registros.xml");
	         	         
	         //escribir la respuesta 
	         OutputStream out = response.getOutputStream();

	         try (FileInputStream in = new FileInputStream(path); BufferedInputStream bin = new BufferedInputStream(in)) {
	             byte[] buffer = new byte[1024];
	             int bytesRead;
	             while ((bytesRead = bin.read(buffer)) != -1) {
	                 out.write(buffer, 0, bytesRead);
	             }
	         }
	         out.flush();
	         out.close();
	         //termina la respuesta
	         
	         rs.close();
	         stmt.close();
	         con.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		  response.setContentType("application/octet-stream"); //para mostrar el contenido en web
		  response.setHeader("Content-Disposition", "attachment; filename=prestaciones.xml"); //ataca al archivo registros.xml
	      
		  return new ModelAndView("redirect:/");	
	}

	//SUBIR FICHERO CSV (EXTRAER INFORMACIÓN DEL FICHERO)
	@RequestMapping(value = "/subir", method = RequestMethod.POST)
	public ModelAndView subircsvPrestacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("Subido");
		
		Part file = request.getPart("file");
		
		String fileName = file.getSubmittedFileName();
		System.out.println("Nombre del archivo >> " + fileName);
		
		long longFile = file.getSize();
		
		String home = System.getProperty("user.home");
		String path = home + "/Downloads/" + fileName;		
		
		try {
			FileOutputStream fos = new FileOutputStream(path);
			InputStream is = file.getInputStream();
			
			byte[] buffer = new byte[1024];
			int leidos;
			
			while((leidos = is.read(buffer)) != -1) {
				fos.write(buffer, 0, leidos);
			}
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		leerArchivoDiferentes(path);
		
		//borrar archivo
		try {
			File f = new File(path);
			f.delete();
		}
		catch(Exception e) {}
		System.out.println("Información subida a la base de datos.");
		
		
		return new ModelAndView("redirect:/");
	}

	public void leerArchivoDiferentes(String nombreArchivo) { //lectura del fichero: Si existe; modifica. Si no existe; inserta
				
		PreparedStatement ps = null;
		boolean hecho = false;
		try {
			br = new BufferedReader(new FileReader(nombreArchivo));
			while((linea = br.readLine()) != null) {
				partes = linea.split(",");
				Prestaciones prestaciones = null;
				for(int i=0; i<partes.length; i++) {
					if(partes.length == 3) { //si en el csv hay registros con 3 campos nada mas (DNI, nombre, apellido) automaticamente lo pone en inactivo
						prestaciones = new Prestaciones(partes[0].toUpperCase(), partes[1].toUpperCase(), partes[2].toUpperCase());
					} else {
						prestaciones = new Prestaciones(Long.parseLong(partes[0]),partes[1].toUpperCase(),partes[2].toUpperCase(),partes[3].toUpperCase(),partes[4].toUpperCase(),partes[5].toUpperCase(),Integer.parseInt(partes[6]),Integer.parseInt(partes[7]),partes[8].toUpperCase(),partes[9].toUpperCase(),Float.parseFloat(partes[10]),Float.parseFloat(partes[11]));
					}
				}
				
		        
		        ps = con.prepareStatement("SELECT * FROM datos WHERE dni = ?");
		        ps.setString(1, prestaciones.getDni());
		        ResultSet rs = ps.executeQuery();
		        
		        //si la clave primaria existe, actualiza el registro
		        if(rs.next()){ 
		        	if(prestacionesDAO.actualizar(prestaciones) > 0) {
		        		
		        		System.out.println("Registro con DNI '" + prestaciones.getDni() + "' existente. Datos modificados con éxito.");
		        	} else {
		        		System.out.println("No se ha podido modificar el registro: '" + prestaciones.getDni() + "'.");
		        	}
		        } else { //si no existe, insertar en la base de datos
		        	if(prestacionesDAO.insertar(prestaciones) > 0) {
		        		System.out.println("Registro con DNI: '" + prestaciones.getDni() + "' insertado con éxito.");
		        	} else {
		        		System.out.println("No se ha podido insertar el registro: '" + prestaciones.getDni() + "'.");
		        	}
		        }
			}
		}
		catch(Exception ex) {
			System.out.println("Ha ocurrido un error al intentar hacer las consultas. " + ex);
		}
	}

	//BUSCAR POR FILTROS
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public ModelAndView buscarPrestacion(ModelAndView model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ArrayList<Prestaciones> encontrado = new ArrayList<>();
		encontrado.clear();
		totalCuantiaActivo = 0;
		totalAtrasoActivo = 0;
		totalAtraso = 0;
		totalCuantia = 0;
		PreparedStatement ps = null;
			
		Long segsocial = (long) 0;
		String sSegsocial = request.getParameter("buscasegsocial");
		if(sSegsocial != null && !sSegsocial.isEmpty()) {
			segsocial = Long.parseLong(sSegsocial);
		}
			
		String dnii = request.getParameter("buscadni").toUpperCase();
		String nombre = request.getParameter("buscanombre").toUpperCase();
		String apellidos = request.getParameter("buscaapellidos").toUpperCase();
		String provincia = request.getParameter("buscaprovincia").toUpperCase();
		String calle = request.getParameter("buscacalle").toUpperCase();
			
		int numero = 0;
		String sNumero = request.getParameter("buscanumero");
		if(sNumero != null && !sNumero.isEmpty()) {
			numero = Integer.parseInt(sNumero);
		}
			
		int codpostal = 0;
		String sCodpostal = request.getParameter("buscacodpostal");
		if(sCodpostal != null && !sCodpostal.isEmpty()) {
			codpostal = Integer.parseInt(sCodpostal);
		}
		String iban = request.getParameter("buscaiban").toUpperCase();
		String entidad = request.getParameter("buscaentidad").toUpperCase();
		String cuenta = request.getParameter("buscacuenta");
			
		float cuantia = 0;
		String sCuantia = request.getParameter("buscacuantia");
		if(sCuantia != null && !sCuantia.isEmpty()) {
			cuantia = Float.parseFloat(sCuantia);
		}
			
		float atraso = 0;
		String sAtraso = request.getParameter("buscaatraso");
		if(sAtraso != null && !sAtraso.isEmpty()) {
			atraso = Float.parseFloat(sAtraso);
		}
			
		String sql = "SELECT * FROM datos WHERE 1=1";
			
		if(sSegsocial != null && !sSegsocial.isEmpty()) {
			sql += "AND seguridadsocial = ?";
		}
		
		if(dnii != null && !dnii.isEmpty()) {
			sql += "AND dni LIKE ?";
		}
			
		if(nombre != null && !nombre.isEmpty()) {
			sql += "AND nombre LIKE ?";
		}
			
		if(apellidos != null && !apellidos.isEmpty()) {
			sql += "AND apellidos LIKE ?";
		}
			
		if(provincia != null && !provincia.isEmpty()) {
			sql += "AND provincia LIKE ?";
		}
			
		if(calle != null && !calle.isEmpty()) {
			sql += "AND calle LIKE ?";
		}
			
		if(sNumero != null && !sNumero.isEmpty()) {
			sql += "AND numero = ?";
		}
			
		if(sCodpostal != null && !sCodpostal.isEmpty()) {
			sql += "AND codigopostal = ?";
		}
			
		if(iban != null && !iban.isEmpty()) {
			sql += "AND iban LIKE ?";
		}
			
		if(entidad != null && !entidad.isEmpty()) {
			sql += "AND entidad LIKE ?";
		}
			
		if(cuenta != null && !cuenta.isEmpty()) {
			sql += "AND cuenta LIKE ?";
		}
			
		if(sCuantia != null && !sCuantia.isEmpty()) {
			sql += "AND cuantia = ?";
		}
			
		if(sAtraso != null && !sAtraso.isEmpty()) {
			sql += "AND atraso = ?";
		}
			
			
		try {	        		        
			ps = con.prepareStatement(sql + "ORDER BY inactivo ASC");
			int i=1;
				
			if(segsocial != 0 && segsocial > 0) {
				ps.setLong(i++, segsocial);
			}
				
			if(dnii != null && !dnii.isEmpty()) {
				ps.setString(i++, dnii + "%");
			}
				
			if(nombre != null && !nombre.isEmpty()) {
				ps.setString(i++, nombre + "%");
			}
				
			if(apellidos != null && !apellidos.isEmpty()) {
				ps.setString(i++, apellidos + "%");
			}
				
			if(provincia != null && !provincia.isEmpty()) {
				ps.setString(i++, provincia + "%");
			}
				
			if(calle != null && !calle.isEmpty()) {
				ps.setString(i++, calle + "%");
			}
				
			if(numero != 0 && numero > 0) {
				ps.setInt(i++, numero);
			}
				
			if(codpostal != 0 && codpostal > 0) {
				ps.setInt(i++, codpostal);
			}
				
			if(iban != null && !iban.isEmpty()) {
				ps.setString(i++, iban + "%");
			}
				
			if(entidad != null && !entidad.isEmpty()) {
				ps.setString(i++, entidad + "%");
			}
				
			if(cuenta != null && !cuenta.isEmpty()) {
				ps.setString(i++, cuenta + "%");
			}
				
			if(cuantia != 0 && cuantia > 0) {
				ps.setFloat(i++, cuantia);
			}
				
			if(atraso != 0 && atraso > 0) {
				ps.setFloat(i++, atraso);
			}
				
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Prestaciones prestaciones = new Prestaciones(rs.getLong("seguridadsocial"), rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("provincia"), rs.getString("calle"), rs.getInt("numero"), rs.getInt("codigopostal"), rs.getString("iban"), rs.getString("entidad"), rs.getFloat("cuantia"), rs.getFloat("atraso"), rs.getBoolean("inactivo"));
				encontrado.add(prestaciones);
			}
				
		}
		catch(SQLException ex) {
			System.out.println("Ha ocurrido un error al intentar hacer la búsqueda por filtros: " + ex);
			ex.printStackTrace();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
			
		System.out.println("\nSe han encontrado: " + encontrado.size() + " registros con dicho valor.\n");
		for(Prestaciones cada: encontrado) {
			System.out.println(cada);
		}
		
		//recoger el total de atrasos
				//double totalAtraso = 0;
				for(Prestaciones cada: encontrado) {
					totalAtraso += cada.getAtraso();
				}
					
				//recoger el total de cuantias
				//double totalCuantia = 0;
				for(Prestaciones cada: encontrado) {
					totalCuantia += cada.getCuantia();
				}
							
				//recoger el total de atrasos (SOLO ACTIVOS)
				//double totalAtrasoActivo = 0;
				for(Prestaciones cada: encontrado) {
					if(!cada.isInactivo()) { //si esta inactivo
						totalAtrasoActivo += cada.getAtraso();
					}
				}
							
				//recoger el total de cuantía (SOLO ACTIVOS)
				//double totalCuantiaActivo = 0;
				for(Prestaciones cada: encontrado) {
					if(!cada.isInactivo()) { //si esta inactivo
						totalCuantiaActivo += cada.getCuantia();
					}
				}
		
		
		System.out.println("Total cuantía activos: " + totalCuantiaActivo);
		System.out.println("Total atraso activos: " + totalAtrasoActivo);
		
		
		
		model.addObject("listadoPrestaciones", encontrado);
		model.addObject("totalcuantiaactivos", totalCuantiaActivo);
		model.addObject("totalatrasoactivos", totalAtrasoActivo);
		model.addObject("totalcuantia", totalCuantia);
		model.addObject("totalatraso", totalAtraso);
		
		model.setViewName("index");
		return model;
	}
}
