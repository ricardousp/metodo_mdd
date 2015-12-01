
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="generated.beans.*"%>
<%@page import="generated.daos.*"%>
<%@page import="java.util.*"%>
<%@page import="core.database.*"%>

<%@page import="generated.usercontent.*"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../css/navigation.css" />
<title>Colegas de trabalho</title>
<script type="text/javascript" src="../js/util.js"></script>
</head>
<body>

<table class="navMainTable">
<tr><td class="navTopArea" colspan="3">
<!-- begin top -->

	
	 
	
<% { %>

	 
	
			
				
				<a href="index.jsp">principal</a>
				|
				<a href="verApoio.jsp">apoio</a>
				|
				<a href="todasNoticias.jsp">noticias</a>
				|
				<a href="projetos.jsp">projetos</a>
				|
				<a href="colegas.jsp">colegas</a>
				|
				<a href="linksInteressantes.jsp">links</a>
				
			
			
		<br/>
	 
	
	
	
<% } %>

<!-- end top -->
</td></tr>
<tr><td class="navLeftArea">
<!-- begin left -->

<!-- end left -->
</td>
<td class="navMainArea">
<!-- begin main -->

	
	
	
<% { %>

<div class="componentTitle">Colegas de trabalho</div>

	 
	 
	
		<%
		ColegaDAO dao = DefaultDAOFactoryProvider.getDefaultDAOFactoryInstance().getColegaDAO();
		List<Colega> listColega = dao.getAllColegas();
		
		for(Colega aColega : listColega) {
		
		%>
		<div class="summaryItem">
		<span class="summaryTitle">
		
			<a href="colega.jsp?idParameter=<%=aColega.getColegaId()%>&entityName=Colega" class="summaryTitle">
		
		
		<%=aColega.getNome()%>
		
			</a>
		
		</span><br/>
		
			<span class="summaryField">
			
				<a href="colega.jsp?idParameter=<%=aColega.getColegaId()%>&entityName=Colega" class="summaryField">
			
			
			<%=aColega.getHomepage()%>
			
				</a>
			
			</span><br/>
		
		</div>
		<% } %>
	
	
	
	
<% } %>

<!-- end main -->
</td>
<td class="navRightArea">
<!-- begin right -->

<!-- end right -->
</td></tr>
<tr><td class="navBottomArea" colspan="3">
<!-- begin bottom -->

<!-- end bottom -->
</td></tr>
</table>

</body>
</html>