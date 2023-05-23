<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.ArrayList" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Formulario</title>
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

<style>
	.scroll{
		max-width: 100%;
        height: 30vh;
        overflow-y: scroll;
	}

    .alinear{
        text-align: center;
    }

    .margintop{
        margin-top: 2vh;
    }
</style>

</head>
<body>
	<c:set var="dnilong" value="${param.dni}" />
    <div class="container-fluid margintop">
        <div class="row">
            
            <div class="col-sm-12">
                <h1 class="alinear">
                </h1>
            </div>
            <div class="col-md-11 col-sm-12 mx-auto margintop">
            
                <form:form action="guardar" method="post" modelAttribute="formPrestacion">
                
                	<form:hidden path="id"/>
                    <!-- top row -->
                    <div class="row">
                        <!-- izquierda -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- dni -->
                            <div class="mb-3">
                    			<label class="form-label">DNI</label>
                    				<c:choose>
                    					
  										<c:when test="${fn:length(dnilong) > 0}">
  											
                    						<form:input path="dni" name="dni" type="text" class="form-control" value="" readonly="true"/>
                    			
                    					</c:when>
  										<c:otherwise>
  											<form:input path="dni" name="dni" type="text" class="form-control" value="" />
  										
  									 	</c:otherwise>
									</c:choose>
                    		</div>
                        </div>
                        <!-- centro izq -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- seguridad social -->
                            <div class="mb-3">
                                <label class="form-label">Seguridad Social</label>
                                <form:input path="seguridadsocial" name="seguridadsocial" type="number" class="form-control" value=""/>
                            </div>
                        </div>
                        <!-- centro der -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- nombre -->
                            <div class="mb-3">
                    			<label class="form-label">Nombre</label>
                    			<form:input path="nombre" name="nombre" type="text" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- derecha -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- apellidos -->
                            <div class="mb-3">
                    			<label class="form-label">Apellidos</label>
                    			<form:input path="apellidos" name="apellidos" type="text" class="form-control" value=""/>
                    		</div>
                        </div>
                    </div>
                    <!-- second row -->
                    <div class="row">
                        <!-- izquierda -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- provincia -->
                            <div class="mb-3">
                    			<label class="form-label">Provincia</label>
                    			<form:input path="provincia" name="provincia" type="text" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- centro izq -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- calle -->
                            <div class="mb-3">
                    			<label class="form-label">Calle</label>
                    			<form:input path="calle" name="calle" type="text" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- centro der -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- número -->
                            <div class="mb-3">
                    			<label class="form-label">Número</label>
                    			<form:input path="numero" name="numero" type="number" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- derecha -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- código postal -->
                            <div class="mb-3">
                    			<label class="form-label">Código Postal</label>
                    			<form:input path="codigopostal" name="codigopostal" type="number" class="form-control" value=""/>
                    		</div>
                        </div>
                    </div>
                    <!-- thrid row -->
                    <div class="row">
                        <!-- izquierda -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- iban -->
                            <div class="mb-3">
                    			<label class="form-label">IBAN</label>
                    			<form:input path="iban" name="iban" type="text" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- centro izq -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- entidad -->
                            <div class="mb-3">
                    			<label class="form-label">Entidad</label>
                    			<form:input path="entidad" name="entidad" type="text" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- centro der -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- cuantía -->
                            <div class="mb-3">
                    			<label class="form-label">Cuantía</label>
                    			<form:input path="cuantia" name="cuantia" type="number" step="0.01" class="form-control" value=""/>
                    		</div>
                        </div>
                        <!-- derecha -->
                        <div class="col-md-3 col-sm-12">
                        	<!-- atraso -->
                            <div class="mb-3">
                    			<label class="form-label">Atraso</label>
                    			<form:input path="atraso" name="atraso" type="number" step="0.01" class="form-control" value=""/>
                    		</div> 
                        </div>
                    </div>
                   
                    
                    <div class="alinear margintop">
                    	<input type="submit" value="Guardar" class="btn btn-primary btn-lg"/>
						
						<a href="/"><input class="btn btn-danger btn-lg" type="button" style="display: inline-block; text-decoration: none;" value="Volver"></a>
                  	</div>
                </form:form>
            </div>
        </div>  
        
        <!-- INCIDENCIAS -->
		<div class="row" style="margin-top: 10vh;">
			<h2 class="alinear">Incidencias</h2>
			<div class="col-md-10 col-sm-12 scroll mx-auto">
					<table class="table table-hover">
						  <thead>
							    <tr style="text-align: center;">
							    	<th scope="col-2">Fecha</th>
							      	<th scope="col-2">Acción</th>
							      	<th scope="col-8">Valor(es) anterior(es)</th>
							    </tr>
						  </thead>
						  <tbody>
						  		<c:forEach items="${listaHistorial}" var="historial" varStatus="status">
						  			<tr style="border-style:solid;">
							    		<td style="text-align: center;">
							    			<fmt:formatDate value="${historial.fecha}" pattern="dd/MM/yyyy HH:mm:ss" />
							    		</td>
							    		<td style="text-align: center;">
							    			${historial.accion}
							    		</td> 
							    		<td style="text-align: center;">
							    			<c:set var="pares" value="${fn:split(historial.valoranterior, ',')}"/>
      										<c:forEach items="${pares}" var="par">
      										    <c:set var="claveValor" value="${fn:split(par, ':')}"/>
      										    <b>${claveValor[0]} </b>${claveValor[1]}<br>
      										</c:forEach>
    									</td>							    
							    	</tr>
							    </c:forEach>
						  </tbody>
					</table>
			</div>
		</div>
    </div>
</body>
</html>