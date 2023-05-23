<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestor de Prestaciones</title>
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1">
<!-- bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- acción cerrar ventanas informativas -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

<style type="text/css">
	.centrar{
		text-align: center;
	}
	.derecha{
		text-align: right;
	}
		
	.scroll{
	    max-width: 100%;
	    height: 450px;
	    overflow-y: scroll;
	}  
	
	.container-fluid{
		margin-left: auto;
		margin-right: auto;
	}
	    
	.responsive{
	    margin-left: auto;
	    margin-right: auto;
	}
	    
	.margintop{
	    margin-top: 2vh;
	}
	    
	.marginbot{
	    margin-bottom: 2vh;
	}
	
	.inactivo{
		background-color: #D4D4D4;
		color: white;
	}	
	.center {
		justify-content: center;
	}
</style>
</head>
<body style="text-align: center;">
	<div class="container-fluid margintop"> <!-- ocupa el completo de la pantalla -->
		<div class="row">
			<div class="col-12">
				<h1 class="margintop marginbot">Prestaciones Spring</h1>
			</div>
			
			<!-- DESCARGAR XML Y EXTRAER CSV -->
			<div class="container responsive">
				
				<div class="row">
					
					<div class="col-3"></div>
					<!-- GENERAR XML -->
					<div class="col-lg-2 col-sm-12 mx-auto">
						<form action="descargar" method="POST" enctype="multipart/form-data">
							<div class="d-grid gap-2">
  								<input type="submit" name="xmlfile" value="Descargar XML" class="btn btn-primary" data-bs-toggle="modal">
							</div>	
						</form>
					</div>
					<div class="col-lg-2"></div>
					
					<!-- IMPORTAR CSV -->
					<div class="col-lg-5 col-sm-12 max-auto center">
						<form action="subir" method="POST" enctype="multipart/form-data" class="row g-3">
  							<div class="col-auto col-xs-8">
  						  		<input class="form-control" type="file" id="formFile" name="file"/>
  							</div>
  							<div class="col-auto col-xs-4">
  								<button class="btn btn-outline-success" type="submit"><i class="fa fa-upload"></i></button>
  							</div>
						</form>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	
	<div class="container-fluid" style="align-content: center;">
		<div class="row">
			<!-- buscador -->
			<div class="col-sm-12">
				<table class="table">
					<tbody>
						<tr>
							<td>
							<!-- buscador por filtros -->
      							<form:form method="POST" action="buscar" class="d-flex margintop">
      								<!-- seguridad social -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Seguridad Social" aria-label="Search" name="buscasegsocial">
        							<!-- dni -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="DNI" aria-label="Search" name="buscadni">
        							<!-- nombre -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Nombre" aria-label="Search" name="buscanombre">
        							<!-- apellidos -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Apellidos" aria-label="Search" name="buscaapellidos">
        							<!-- provincia -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Provincia" aria-label="Search" name="buscaprovincia">
        							<!-- calle -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Calle" aria-label="Search" name="buscacalle">
        							<!-- numero -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Número" aria-label="Search" name="buscanumero">
        							<!-- codigo postal -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Código postal" aria-label="Search" name="buscacodpostal">
        							<!-- iban -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="IBAN" aria-label="Search" name="buscaiban">
        							<!-- sucursal -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Entidad" aria-label="Search" name="buscaentidad">
        							<!-- cuantía -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Cuantía" aria-label="Search" name="buscacuantia">
        							<!-- atraso -->
        							<input class="form-control form-control-sm me-2" type="search" placeholder="Atraso" aria-label="Search" name="buscaatraso">
        							
        							<button class="btn btn-outline-success" type="submit"><i class="fa fa-search"></i></button>
      								<!-- <input type="hidden" name="accion" value="buscarfiltro"/> -->
      							</form:form> 
							</td>							
						</tr>
					</tbody>
					
				</table>
			</div>
		</div>
	
	</div>
	
	<div class="container-fluid" style="align-content: center;">
		<div class="col-12 scroll">
			<table class="table table-bordered">
				<thead>
			    	<tr class="table-dark">
			      		<th scope="col">Seguridad Social</th>
			      		<th scope="col">DNI</th>
			      		<th scope="col">Nombre</th>
			      		<th scope="col">Apellidos</th>
			      		<th scope="col">Provincia</th>
			      		<th scope="col">Calle</th>
			      		<th scope="col">Número</th>
			      		<th scope="col">Código Postal</th>
			      		<th scope="col">IBAN</th>
			      		<th scope="col">Entidad</th>
			      		<th scope="col">Cuantía</th>
			      		<th scope="col">Atraso</th>
			      		<th colspan="2">Acciones</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<c:forEach items="${listadoPrestaciones}" var="prestaciones" varStatus="status">
			  			<tr class="${prestaciones.inactivo?'inactivo':''}">
			  				<td class="align-middle">${prestaciones.seguridadsocial}</td>
			  				<td class="align-middle">${prestaciones.dni}</td>
			  				<td class="align-middle">${prestaciones.nombre}</td>
			  				<td class="align-middle">${prestaciones.apellidos}</td>
			  				<td class="align-middle">${prestaciones.provincia}</td>
			  				<td class="align-middle">${prestaciones.calle}</td>
			  				<td class="align-middle">${prestaciones.numero}</td>
			  				<td class="align-middle">${prestaciones.codigopostal}</td>
			  				<td class="align-middle">${prestaciones.iban}</td>
			  				<td class="align-middle">${prestaciones.entidad}</td>
			  				<td class="align-middle"><fmt:formatNumber value="${prestaciones.cuantia}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</td>
			  				<td class="align-middle"><fmt:formatNumber value="${prestaciones.atraso}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</td>
			  				
			  				<!-- BOTONES DE EDITAR Y ACTIVAR/DESACTIVAR -->
			  				<td>
			  					<!-- EDITAR -->
			  					<form:form action="formulario" method="get" modelAttribute="formPrestacion">
    								<input type="hidden" name="accion" value="editar"/>
    								<input type="hidden" name="dni" value="${prestaciones.dni}"/>
    								<input type="submit" value="Editar" class="btn btn-warning">
								</form:form>
			  					<!-- ACTIVAR / DESACTIVAR -->
			  					<form:form action="estado" method="get">
			  						<input type="hidden" path="accion" value="eliminar"/>
    								<input type="hidden" name="dni" value="${prestaciones.dni}"/>
    								<input type="submit" value="${prestaciones.inactivo?'Activar':'Desactivar'}" class="${prestaciones.inactivo?'btn btn-dark':'btn btn-danger'}">
			  					</form:form>
			  				</td>
			  			</tr>
			  		</c:forEach>
			  	</tbody>
			</table>
		</div>
	</div>
	
	<div class="container" style="margin-right: 2vh;">
		<div class="row">
			<div class="col-7"></div>
			<div class="col-4">
				<table class="table border" style="max-heigth: 100%;">
					<!-- TOTAL CUANTIA Y ATRASOS -->
				  	<tbody>
	            		<tr>
	            		    <th scope="row" style="text-align:right" colspan="4">Totales (activos)</th>
	            		    <td class="fw-bold derecha" colspan="3"><fmt:formatNumber value="${totalcuantiaactivos}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</td>
	            		    <td class="fw-bold"><fmt:formatNumber value="${totalatrasoactivos}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</td>
	            		</tr>
	            		<tr>
	            		    <th scope="row" style="text-align:right" colspan="4">Totales</th>
	            		    <td class="fw-bold derecha" colspan="3"><fmt:formatNumber value="${totalcuantia}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</td>
	            		    <td class="fw-bold"><fmt:formatNumber value="${totalatraso}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</td>
	            		</tr>
	        		</tbody>
				</table>
			</div>
		</div>
		
	</div>
	<!-- INSERTAR REGISTRO -->
	<div class="container responsive" style="margin-top: 2vh;">
		<div class="row marginbot">
			<div class="col-12">
				<form action="formulario" method="GET">
					<div class="d-grid gap-2">
						<input type="submit" value="Insertar registro" class="btn btn-success">
					</div>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>